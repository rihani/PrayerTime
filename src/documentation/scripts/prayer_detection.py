#!/usr/bin/env python

import spidev
import time
import RPi.GPIO as GPIO
import socket
import sys  #for exit
import signal

debug = False

radar_adc = 0
pause_delay = 10
resend_msg_delay = 15 #=======> change to 60 minimum
initial = False
start_time = time.time()
light = False
msg_resend = False

port = 8888;
HOST = "localhost"

# GPIO.setmode(GPIO.BOARD) ## Use board pin numbering
GPIO.setmode(GPIO.BCM)
GPIO.setup(4, GPIO.OUT) ## Setup GPIO Pin 7 to OUT
GPIO.setup(25, GPIO.OUT)
GPIO.setwarnings(False)
spi = spidev.SpiDev()
spi.open(0,0)

# create dgram udp socket
try:
	s1 = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	s1.bind(('', 0))
	s1.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
except socket.error:
	print 'Failed to create socket'


try:		
	s2 = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	
except socket.error:
	print 'Failed to create local socket'

 

# read SPI data from MCP3008 chip, 8 possible adc's (0 thru 7)
def readadc(adcnum):
	if ((adcnum > 7) or (adcnum < 0)):
		return -1
	r = spi.xfer2([1,(8+adcnum)<<4,0])
	adcout = ((r[1]&3) << 8) + r[2]
	return adcout


def signal_handler(signal, frame):
	print 'Program terminated!'
	GPIO.output(4,True)
	time.sleep(0.25)
	GPIO.output(4,False)
	time.sleep(0.25)
	GPIO.output(4,True)
	time.sleep(0.25)
	GPIO.output(4,False)
	time.sleep(0.25)
	GPIO.output(4,True)
	time.sleep(0.25)
	GPIO.output(4,False)
	GPIO.output(25,False)
	GPIO.cleanup()
	s1.close()
	s2.close()
	sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

while True:
	try:
		timer = time.time()
		r = []
		for i in range (0,10):
			r.append(readadc(radar_adc))
		pir_value = int(round((sum(r)/10.0) / 10.24))# round out decimal value, cast volume as integer
		
		if debug:
			print 'Radar = {radar}%' .format(radar = pir_value)			

		if pir_value > 15 and not initial:
			GPIO.output(25,True)
			initial = True
			start_time = time.time()

		elif pir_value < 15:
			GPIO.output(25,False)
			initial = False
			msg_resend = False
			if light:
				GPIO.output(4,False) ## Turn off GPIO pin 7
				light = False

		if initial and timer - start_time > pause_delay and not light:
			initial = False
			GPIO.output(4,True) ## Turn on GPIO pin 7
			light = True
			if debug:
				print 'Prayer in progress'
			msg = 'Prayer in progress'
			msg_resend = True
			resend_msg_delay_timer = time.time()
			
			try:
				s1.sendto(msg, ('<broadcast>', port))
 			except:
 				print 'Failed to sent message'
 				print 'trying on localhost'
 				try:
 					msg = 'Prayer in progress'
 					s2.sendto(msg, (HOST,port))
 				except:
 					print 'Failed to send message on localhost'

		time.sleep(0.5)
		if msg_resend and timer - resend_msg_delay_timer > resend_msg_delay and light:
			if debug:
				print 'Prayer in progress'
			msg = 'Prayer in progress'
			msg_resend = True
			resend_msg_delay_timer = time.time()
			
			try:
				s1.sendto(msg, ('<broadcast>', port))
 			except:
 				print 'Failed to sent message'
 				print 'trying on localhost'
 				try:
 					msg = 'Prayer in progress'
 					s2.sendto(msg, (HOST,port))
 				except:
 					print 'Failed to send message on localhost'

	except KeyboardInterrupt:
		GPIO.output(4,True)
		time.sleep(0.25)
		GPIO.output(4,False)
		time.sleep(0.25)
		GPIO.output(4,True)
		time.sleep(0.25)
		GPIO.output(4,False)
		time.sleep(0.25)
		GPIO.output(4,True)
		time.sleep(0.25)
		GPIO.output(4,False)
		GPIO.output(25,False)
		GPIO.cleanup()
		s1.close()
		s2.close()
		sys.exit()
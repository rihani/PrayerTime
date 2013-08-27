package javafxapplication4;

// -*-Java-*-

/**
 *
 * Moon determines the fraction of the moon's disk that is illuminated
 * at a given time.  I might extend this to do more calculations some
 * time in the future.<p>
 *
 * One exciting web site to check is the USNO, at
 * <a href="http://aa.usno.navy.mil/AA/data/docs/MoonFraction.html">
 * http://aa.usno.navy.mil/AA/data/docs/MoonFraction.html
 * </a>.<p>
 *
 * Searches under "illuminated fraction of moon" seem to hit very
 * well.
 *
 # @author Kleanthes Koniaris
 # @see com.koniaris.astronomy.JulianDay
 */

public class Moon {

    private double t;
    private double i;

    // ----------------------------------------------------------------------

    /** Creates a moon at the current time. */

    public Moon() throws Exception {
        this(new JulianDay());
    }

    // ----------------------------------------------------------------------

    /** Creates a moon at a given point in time given by a
        JulianDay.

        @see com.koniaris.astronomy.JulianDay
    */
    public Moon(JulianDay j) {
        t = (j.julianDay() - 2451545.0) / 36525.0; // See Meeus 22.1
        i = i(t); // the phase angle
    }

    // ----------------------------------------------------------------------

    /** Returns a boolean if the moon is waning (getting
     * darker). Meeus didn't have a formula for this, but it seemed
     * obvious (famous last words?  ;) */

    public boolean isWaning() {return isWaning(i);}

    public static boolean isWaning(double i) { // you pass i(t)
        // let's get the angle
        double s = sin(i);
        if (s < 0) return true; // wanes (shrinks)
        if (s > 0) return false; // waxes (grows)
        // d'oh, now we're either at the absolute full moon or new moon!
        return cos(i) > 0;
    }

    // ----------------------------------------------------------------------

    /** Returns the illuminated fraction of the moon's surface. */

    public double illuminatedFraction() {return illuminatedFraction(i);}

    public static double illuminatedFraction(double i) {
        return (1 + cos(i)) * 0.5;
    }    

    // ----------------------------------------------------------------------

    /** Returns the percentage of the moon's surface that is
        illuminated as an integer that expects to be put next to a
        percent sign (%) during presentation */

    public int illuminatedPercentage() {
        return illuminatedPercentage(i);
    }

    public static int illuminatedPercentage(double i) {
        int p = (int) Math.round(100.0 * illuminatedFraction(i));
        // I should assert that it is properly bounded...
        return p;
    }

    // ----------------------------------------------------------------------

    /** Returns sine from an angle in degrees. */

    private static double sin(double x) {
        return Math.sin(Math.toRadians(x));
    }

    // ----------------------------------------------------------------------

    /** Returns cosine from an angle in degrees. */

    private static double cos(double x) {
        return Math.cos(Math.toRadians(x));
    }

    // ----------------------------------------------------------------------

    /** Determines the phase angle between the sun, moon and earth.
        See Meeus 41.1. */

    public double i() {
        return i;
    }

    public static double i(double t) {
        double d = d(t);
        double m = m(t);
        double mPrime = mPrime(t);
        double i = 180.0
            - d
            - 6.289 * sin(mPrime) 
            + 2.100 * sin(m)
            - 1.274 * sin(2.0 * d - mPrime)
            - 0.658 * sin(2.0 * d)
            - 0.214 * sin(2.0 * mPrime)
            - 0.110 * sin(d);
        return i;
    }

    // ----------------------------------------------------------------------

    /** Mean elongation of the moon.  See Meeus 47.2. */

    public double d() {return d(t);}

    public static double d(double t) {
        return 297.8501921 + t * (445267.1114034 + t * (-0.0018819 + t * (1.0 / 545868.0 - t / 113065000.0)));
    }

    // ----------------------------------------------------------------------

    /** Sun's mean anomaly.  See Meeus 47.3. */

    public double m() {return m(t);}

    public static double m(double t) {
        return 357.5291092 + t * (35999.0502909 + t * (-0.0001536 + t / 24490000.0));
    }

    // ----------------------------------------------------------------------

    /** Moon's mean anomaly.  See Meeus 47.4. */

    public double mPrime() {return mPrime(t);}

    public static double mPrime(double t) {
        return 134.9633964 + t * (477198.8675055 + t * (0.0087414 + t * (1.0 / 69699.0 - t / 14712000.0)));
    }

    // ----------------------------------------------------------------------

    /** This demo shows the fraction of the moon that is illuminated at a given date. */

    public static void main(String[] arg)
        throws Exception {
        JulianDay j = new JulianDay();
        switch (arg.length) {
        case 3:
            j = new JulianDay(new Integer(arg[0]).intValue(), new Integer(arg[1]).intValue(), new Double(arg[2]).doubleValue());
            break;
        case 0:
            // we set it to the current time, above
            break;
        default:
            throw new Exception("give YYYY MM DD to see the fraction of the moon that is full.");
        }
        Moon m = new Moon(j);
        System.out.println("The moon is " + m.illuminatedPercentage() + "% full and " + (m.isWaning() ? "waning" : "waxing"));
    }
    
    // ----------------------------------------------------------------------

}

package generalUtilities;


/**
 * Some mathematical functions.
 *
 * @author Andreas Wenger
 */
public class MathTools
{

  
  public MathTools()
  {
  }


  /**
   * Computes the lowest common multiple.
   */
  public int lcm(int n1, int n2)
  {
    return (n1 * n2) / gcd(n1, n2);
  }


  /**
   * Computes the lowest common multiple.
   */
  public int lcm(int[] n)
  {
    if (n.length == 1)
      return n[0];
    int res = lcm(n[0], n[1]);
    for (int i = 2; i < n.length; i++)
      res = lcm(res, n[i]);
    return res;
  }


  /**
   * Computes the greatest common divisor.
   */
  public int gcd(int n1, int n2)
  {
    if (n2 == 0)
      return 1;
    int r = n1 % n2;
    return (r == 0) ? n2 : gcd(n2, r);
  }


  /**
   * Computes the greatest common divisor.
   * Everything else than optimated, but should do its job.
   */
  public int gcd(int[] n)
  {
    //first find smallest number of n[]
    int min = n[0];
    for (int i = 1; i < n.length; i++)
    {
      if (n[i] < min)
        min = n[i];
    }
    //now just try it out
    for (int i = min; i > 0; i--)
    {
      boolean ok = true;
      for (int iN = 0; iN < n.length; iN++)
      {
        if ((n[iN] % i) != 0)
        {
          ok = false;
          break;
        }
      }
      if (ok)
        return i;
    }
    return 0;
  }

}


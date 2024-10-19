import java.util.*;
import java.math.BigInteger;

public class ShamirSecretSharing 
{
    public static BigInteger decodeValue(int base, String value) 
    {
        return new BigInteger(value, base);
    }
    public static BigInteger lagrangeInterpolation(List<int[]> points, int k) 
    {
        BigInteger constant = BigInteger.ZERO;
        
        for (int i = 0; i < k; i++) 
        {
            BigInteger yi = BigInteger.valueOf(points.get(i)[1]);
            BigInteger li = BigInteger.ONE; 
            
            for (int j = 0; j < k; j++) 
            {
                if (i != j) 
                {
                    BigInteger xj = BigInteger.valueOf(points.get(j)[0]);
                    BigInteger xi = BigInteger.valueOf(points.get(i)[0]);
                    li = li.multiply(BigInteger.ZERO.subtract(xj)) 
                          .divide(xi.subtract(xj));
                }
            }
            constant = constant.add(yi.multiply(li));
        }
        return constant;
    }

    public static void main(String[] args) 
    {
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> value1 = new HashMap<>();
        value1.put("base", "10");
        value1.put("value", "4");
        data.put("1", value1);
        Map<String, String> value2 = new HashMap<>();
        value2.put("base", "2");
        value2.put("value", "111");
        data.put("2", value2);
        Map<String, String> value3 = new HashMap<>();
        value3.put("base", "10");
        value3.put("value", "12");
        data.put("3", value3);
        Map<String, String> value6 = new HashMap<>();
        value6.put("base", "4");
        value6.put("value", "213");
        data.put("6", value6);
        int n = 4;
        int k = 3;
        List<int[]> points = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            int x = Integer.parseInt(entry.getKey());  // x is the key (integer)
            int base = Integer.parseInt(entry.getValue().get("base"));
            String value = entry.getValue().get("value");
            BigInteger y = decodeValue(base, value);  // Decode y using the base
            
            // Add the point (x, y) to the points list
            points.add(new int[] { x, y.intValue() });
        }

        // Perform Lagrange interpolation to find the constant term (c)
        BigInteger constant = lagrangeInterpolation(points, k);

        // Output the constant term (secret)
        System.out.println("The secret constant term is: " + constant);
    }
}

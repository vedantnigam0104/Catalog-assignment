import java.util.*;
import java.math.BigInteger;

public class ShamirSecretSharing {
    
    // Function to convert a value from a given base to base 10
    public static BigInteger decodeValue(int base, String value) {
        return new BigInteger(value, base);  // Converts value to base 10
    }
    
    // Function to perform Lagrange interpolation to find the constant term (f(0))
    public static BigInteger lagrangeInterpolation(List<int[]> points, int k) {
        BigInteger constant = BigInteger.ZERO; // The constant term we are solving for
        
        for (int i = 0; i < k; i++) {
            BigInteger yi = BigInteger.valueOf(points.get(i)[1]);
            BigInteger li = BigInteger.ONE; // Lagrange basis polynomial
            
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    BigInteger xj = BigInteger.valueOf(points.get(j)[0]);
                    BigInteger xi = BigInteger.valueOf(points.get(i)[0]);
                    
                    // Calculate L_i(0) = product of (-x_j) / (x_i - x_j)
                    li = li.multiply(BigInteger.ZERO.subtract(xj))  // -x_j
                          .divide(xi.subtract(xj)); // (x_i - x_j)
                }
            }
            
            // Add the contribution of y_i * L_i(0) to the constant term
            constant = constant.add(yi.multiply(li));
        }
        
        return constant;
    }

    public static void main(String[] args) {
        // Example input
        Map<String, Map<String, String>> data = new HashMap<>();
        
        // Example case: "1": {"base": "10", "value": "4"}
        Map<String, String> value1 = new HashMap<>();
        value1.put("base", "10");
        value1.put("value", "4");
        data.put("1", value1);
        
        // Example case: "2": {"base": "2", "value": "111"}
        Map<String, String> value2 = new HashMap<>();
        value2.put("base", "2");
        value2.put("value", "111");
        data.put("2", value2);
        
        // Example case: "3": {"base": "10", "value": "12"}
        Map<String, String> value3 = new HashMap<>();
        value3.put("base", "10");
        value3.put("value", "12");
        data.put("3", value3);
        
        // Example case: "6": {"base": "4", "value": "213"}
        Map<String, String> value6 = new HashMap<>();
        value6.put("base", "4");
        value6.put("value", "213");
        data.put("6", value6);

        // n = 4, k = 3 (given in input)
        int n = 4;
        int k = 3;

        // Decode the points
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

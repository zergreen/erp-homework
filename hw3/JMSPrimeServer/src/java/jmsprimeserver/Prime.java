package jmsprimeserver;

/**
 *
 * @author homer
 */
public class Prime {
//    public static void main(String[] args) {
//        // System.out.println("args[0] is " + args[0]);
//
//        // int number = Integer.parseInt(args[0]);
//
//        // number += 0;
//
//        // System.out.println("number is " + number);
//
//        // System.out.println("Is prime or not : " + isPrime(number));
//
//        // int countPrime = countPrime(number);
//
//        System.out.println("number of prime is "+countPrime("1000,1000000"));
//
//    }
    
    public void hello(String foo){
        System.out.println("Bar :"+foo);
    }
    
    public void hello(){
        System.out.println("World");
    }
    
    

    private boolean isPrime(int n) {
        int i;
        for (i = 2; i * i <= n; i++) {
            if ((n % i) == 0) {
                return false;
            }
        }
        return true;
    }

    int countPrime(String input) {
        int count = 0;
        int start = 0;
        int stop = 0;

        // String text = "1000,1000000";
        String text = input;
        String[] parts = text.split(",");
    
        start = Integer.parseInt(parts[0]);
        stop = Integer.parseInt(parts[1]);
    
        // step[2] calculate number of prime 
        for (int i = start; i < stop; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
    
        System.out.println("[Server] number of prime is " + count);
    
        return count;
    }
}
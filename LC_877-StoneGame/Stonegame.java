import java.util.*;

public class Stonegame {
    public boolean stoneGame(int[] piles) {

        // Easiest approach: Alice always wins if she plays optimally
        // because the number of piles is even and the total number of stones is odd.
        // So, we can just return true.
    // return true;
    int left=0;
    int right=piles.length-1;
    int alice=0;
    int bob=0;
    boolean turn=true;

    while(left<=right)
    {     
        if(turn){
            if(piles[left]>piles[right]){
                alice+=piles[left];
                left++;
            }
            else{
                alice+=piles[right];
                right--;
            }
            turn=!turn;
        }
        else{
            if(piles[left]<piles[right])
            {
                bob+=piles[left];
                left++;
            }
            else{
                bob+=piles[right];
                right--;
            }
            turn=!turn;
        }
    }
    return alice>bob;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stonegame obj = new Stonegame();
        int n = sc.nextInt();
        int[] piles = new int[n];
        for (int i = 0; i < n; i++) {
            piles[i] = sc.nextInt();
        }
        System.out.println(obj.stoneGame(piles));
        sc.close();
    }
}

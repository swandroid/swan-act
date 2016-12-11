package interdroid.swan.actuators.tasks;

import android.util.Log;

/**
 * Created by rm on 04/11/2016.
 */

public class BogoSort {

    public BogoSort(int[] i) {
        int counter = 0;
        //System.out.println("I'll sort " + i.length + " elements...");
        Log.d("PRAGUE","BOGOSTART");
        while(!isSorted(i)) {
            shuffle(i);
            counter++;
        }
        Log.d("PRAGUE","BOGOEND");
        //System.out.println("Solution found! (shuffled " + counter + " times)");
        //print(i);
    }

    private void print(int[] i) {
        for(int x : i) {
            //System.out.print(x + ", ");
        }
        //System.out.println();
    }

    private void shuffle(int[] i) {
        for(int x = 0; x < i.length; ++x) {
            int index1 = (int) (Math.random() * i.length),
                    index2 = (int) (Math.random() * i.length);
            int a = i[index1];
            i[index1] = i[index2];
            i[index2] = a;
        }
    }

    private boolean isSorted(int[] i) {
        for(int x = 0; x < i.length - 1; ++x) {
            if(i[x] > i[x+1]) {
                return false;
            }
        }
        return true;
    }


}

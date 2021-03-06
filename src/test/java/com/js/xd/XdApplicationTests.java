package com.js.xd;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XdApplicationTests {

    @Test
    void contextLoads() {
        int[] arr = {1,4,5,2,3,7,1,2,9,6};
        sort(arr,0,arr.length-1);
        System.out.println(arr.toString());
    }

    private void sort(int[] arr,int leftIndex,int rightIndex){
        if (leftIndex>=rightIndex)
            return;
        int left = leftIndex;
        int right = rightIndex;
        int key = arr[left];
        while(left<right){
            while(right>left&&arr[right]>=key){
                right--;
            }
            arr[left] = arr[right];
            while(left<right&&arr[left]<=key){
                left++;
            }
            arr[left] = arr[right];
        }
        arr[left] = key;
        sort(arr,leftIndex,left-1);
        sort(arr,right+1,rightIndex);
    }

}

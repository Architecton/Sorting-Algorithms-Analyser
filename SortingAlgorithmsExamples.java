import java.util.Scanner;
import java.util.ArrayList;

public class SortingAlgorithmsExamples {
	// This program should accept 3 arguments and an optional 4. argument
	// The first argument specifies whether to print the number of swaps performed or the trace of the sort
	// the second argument specifies which sorting algorithm to use (see table of abbreviations)
	// the third argument specifies whether to sort ascending or descending (up, down)
	// the optional fourth argument specifies the length of the array to be sorted
	
	// the array is to be read from the standard input using .nextInt() method of a Scanner instance
	// if no array length is given the array should be allocated dynamically.
	public static void main(String[] args) {
		// parse arguments /////////
		int arrLength = -1;
		String outputAction = args[0];
		String algorithm = args[1];
		String sortDirection = args[2];
		if(args.length == 4) {
			arrLength = Integer.parseInt(args[3]);
		}
		////////////////////////////
		
		// parse the array /////////
		Scanner sc = new Scanner(System.in);
		int[] arr;
		// if length is known in advance
		if(arrLength != -1) {
			arr = new int[arrLength];
			for(int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
		// else, read into ArrayList and then convert to array of primitive ints
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			while(sc.hasNextInt()) {
				temp.add(sc.nextInt());
			}
			arr = temp.stream().mapToInt(i -> i).toArray();
		}
		// test print of parsed arr //////////////
		printArray(arr);
		//////////////////////////////////////////
		
	}

	// printArray: prints array of integers arr
	public static void printArray(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.printf((i == arr.length - 1) ? "%d%n" : "%d, ", arr[i]);
		}
	}
}

// dataGenerator: used to generate data used for algorithm testing
class dataGenerator {
	// generateUnsortedArray: generate array of evenly distributed random integers on [min, max]
	public static int[] generateUnsortedArray(int length, int min, int max) {
		int[] arr = new int[length];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = min + (int)(Math.random() * ((max - min) + 1));
		}
		return arr;
	}
}

class SortingAlgorithms {
	
	public static long swapCounter;
	
	// quicksort: perform the quicksort algorithm on array of integers arr on index interval [leftBound, rightBound]
	public static void quicksort(int[] arr, int leftBound, int rightBound) {
		if(leftBound >= rightBound) {
			return;
		}
		// choose middle element as the pivot value
		int pivot = arr[(leftBound + rightBound)/2];
		// Set left and right pointers
		int l = leftBound;
		int r = rightBound;

		// while pointers have not met
		while(l <= r) {
			// while value pointed to by l is smaller than pivot, move pointer left
			while(arr[l] < pivot) {
				l++;
			}
			// while vale pointed to by l is smaller than pivot, move pointer right
			while(arr[r] > pivot) {
				r--;
			}
			// if l and r have not crossed
			if(l <= r) {
				// swap elements pointed to by l and r
				swap(arr, l, r);
				// move l one element to the right and r one element to the left
				l++;
				r--;
			}
		}
		// recursive call for section of array with values smaller than pivot
		quicksort(arr, leftBound, r);
		// recursive call for section of array with values larger or equal to pivot
		quicksort(arr, l, rightBound);
	}
	// heapsort: performs the heapsort sorting algorithm on the array arr
	public static void heapsort(int[] arr) {
		// start with first node with children and recursively build array representation of heap
		for(int i = arr.length/2 - 1; i >= 0; i--) {
			heapify(arr, i, arr.length);
		}
		// move root of heap to end of array and fix heap
		for(int i = arr.length - 1; i >= 0; i--) {
			swap(arr, 0, i);
			heapify(arr, 0, i);
		}
	}

	// heapify: auxilliary method for recursively building the array representation of a heap
	private static void heapify(int[] arr, int rootIndex, int rightBound) {
		// initialize pointers to root (assumed largest), left child of root and right child of roots
		int largest = rootIndex;
		int leftChild = 2*rootIndex + 1;
		int rightChild = 2*rootIndex + 2;

		// check if any child is than root and let largest point to largest child
		if(leftChild < rightBound && arr[leftChild] > arr[largest]) {
			largest = leftChild;
		}
		if(rightChild < rightBound && arr[rightChild] > arr[largest]) {
			largest = rightChild;
		}
		// if root is not largest, swap and recursively fix heap with root at largest child
		if(largest != rootIndex) {
			swap(arr, largest, rootIndex);
			heapify(arr, largest, rightBound);
		}
	}

	// insertionsort: performs the insertionsort algorithm on the array pointed to by arr
	public static void insertionsort(int[] arr) {
		// assume first element is sorted
		// iterate over array on [1, arr.length - 1]
		for(int i = 1; i < arr.length; i++) {
			// initialize new variable used for insertion
			int j = i;
			// while current element is larger than its predecessor
			while(j > 0 && arr[j] < arr[j - 1]) {
				// swap the two elements and repeat for element originaly pointed to by i
				swap(arr, j, j - 1);
				j--;
			}
		}
	}

	// bubblesort: performs the basic bubblesort algorithm on the array pointed to by arr
	public static void bubblesort(int[] arr) {
		// go over pairs of elements and compare
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr.length - i - 1; j++) {
				if(arr[j] > arr[j + 1]) {
					// if next value in array is smaller, swap
					swap(arr, j, j+1);
				}
			}
		}
	}

	// selectionsort: performs the selectionsort algorithm on the array pointed to by arr
	public static void selectionsort(int[] arr) {
		// outer loop is at boundary of sorted and unsorted part
		for(int i = 0; i < arr.length - 1; i++) {
			// save assumed minimum value and its index
			int min = arr[i];
			int indexMin = i;

			// go over unsorted part and check if any values are smaller than assumed minimum
			for(int j = i + 1; j < arr.length; j++) {
				if(arr[j] < min) {
					min = arr[j];
					indexMin = j;
				}
			}
			// if smaller value found, put in sorted section
			if(i != indexMin) {
				swap(arr, i, indexMin);
			}
		}
	}

	// swap: swaps elements at index1 and index2 in array arr
	private static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
		SortingAlgorithms.swapCounter++;
	}
}
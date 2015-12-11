package test;

import java.util.Random;
import java.util.Scanner;

public class Sort {

	/*
	 * 请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出 1 消耗时间： 2398470ms
	 * 
	 * 请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出 2 消耗时间： 125043ms
	 * 
	 * 请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出 3 消耗时间： 572ms
	 * 
	 * 请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出 4 消耗时间： 264ms
	 * 
	 * 请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出 5 消耗时间： 68ms
	 */

	/*public static void main(String[] args) {
		int[] arr = new int[500000];
		Random random = new Random(1000);
		for (int i = 0; i < 500000; i++) {
			arr[i] = random.nextInt();
		}
		// int[] arr = { 1, 6, 3, 8, 4, 3, 12, 5 };
		Scanner scanner = new Scanner(System.in); // 参数对象是系统进来的流
		int sName = -1;
		while (sName != 0) {
			System.out.println("请选择排序: 1.冒泡 2.选择 3.插入 4.合并 5.快速 0.退出");
			sName = scanner.nextInt(); // next()方法用来接收控制台输入的字符串
			long t = System.currentTimeMillis();
			switch (sName) {
			case 0:
			default:
				System.exit(1);
				break;
			case 1:
				bubbleSort(arr);
				break;
			case 2:
				selectionSort(arr);
				break;
			case 3:
				insertionSort(arr);
				break;
			case 4:
				mergeSort(arr);
				break;
			case 5:
				quickSort(arr);
				break;
			}
			System.out.println("消耗时间： " + (System.currentTimeMillis() - t) + "ms");
			// print(arr);
			System.out.println();
		}

	}*/

	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private static void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	// 冒泡排序
	private static void bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					swap(arr, i, j);
				}
			}
		}
	}

	// 选择排序
	private static void selectionSort(int[] arr) {
		int minIndex = 0;
		for (int i = 0; i < arr.length; i++) {
			minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[minIndex] > arr[j]) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				swap(arr, i, minIndex);
			}
		}
	}

	// 插入排序
	private static void insertionSort(int[] arr) {
		int current = 0;
		for (int i = 1; i < arr.length; i++) {
			current = arr[i];
			for (int j = i - 1; j >= 0; j--) {
				if (current < arr[j]) {
					swap(arr, i, j);
					i--;
				} else {
					break;
				}
			}
		}
	}

	// 归并排序
	private static void mergeSort(int[] arr) {
		int[] tmpArr = new int[arr.length];
		mergeSort(arr, tmpArr, 0, arr.length - 1);
	}

	private static void mergeSort(int[] arr, int[] tmpArr, int left, int right) {
		if (left >= right) {
			return;
		}
		int center = (left + right) / 2;
		mergeSort(arr, tmpArr, left, center);
		mergeSort(arr, tmpArr, center + 1, right);
		merge(arr, tmpArr, left, center, right);
	}

	private static void merge(int[] arr, int[] tmpArr, int left, int center, int right) {
		int startLeft = left;
		int startRight = center + 1;
		int tmpLeft = left;
		while (startLeft <= center && startRight <= right) {
			if (arr[startLeft] < arr[startRight]) {
				tmpArr[tmpLeft++] = arr[startLeft++];
			} else {
				tmpArr[tmpLeft++] = arr[startRight++];
			}
		}
		while (startLeft <= center) {
			tmpArr[tmpLeft++] = arr[startLeft++];
		}
		while (startRight <= right) {
			tmpArr[tmpLeft++] = arr[startRight++];
		}
		while (left <= right) {
			arr[left] = tmpArr[left++];
		}
	}

	// 快速排序
	private static void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int left, int right) {
		if (left >= right) {
			return;
		}
		int partitionPos = (left + right) / 2;
		swap(arr, partitionPos, right);
		int index = left - 1;
		for (int i = left; i < right; i++) {
			if (arr[i] <= arr[right]) {
				swap(arr, ++index, i);
			}
		}
		swap(arr, ++index, right);

		quickSort(arr, left, index - 1);
		quickSort(arr, index + 1, right);
	}

	// 堆排序
	private static void heapSort(int[] arr) {
		int lastIndex = arr.length - 1;
		buildMaxHeap(arr, lastIndex);

		while (lastIndex > 0) {
			swap(arr, 0, lastIndex--);
			percDown(arr, 0, lastIndex);
		}

	}

	private static void buildMaxHeap(int[] arr, int lastIndex) {
		for (int i = lastIndex / 2; i >= 0; i--) {
			percDown(arr, i, lastIndex);
		}
	}

	private static void percDown(int[] arr, int parent, int lastIndex) {
		int child = 0;
		for (; leftChild(parent) <= lastIndex; parent = child) {
			child = leftChild(parent);
			if (child < lastIndex && arr[child] < arr[child + 1]) {
				child++;
			}
			if (arr[parent] < arr[child]) {
				swap(arr, parent, child);
			}
		}
	}

	private static int leftChild(int parent) {
		return parent * 2 + 1;
	}
	
	public static void main(String[] args) {
		int[] arr = { 1, 6, 3, 8, 4, 3, 12, 5 };
		heapSort(arr);
		print(arr);
	}

}

package com.oubowu.exerciseprogram.sort;

import java.util.Random;
import java.util.Scanner;

public class Sort {

	public static void main(String[] args) {

		// int[] arr = { 1, 6, 3, 8, 4, 3, 12, 5 };
		Scanner scanner = new Scanner(System.in);
		// 参数对象是系统进来的流
		int sName = -1;
		int length = 200000;
//		length = 15;
		int[] arr = new int[length];
		Random random = new Random();
		int range = 10000;
		for (int i = 0; i < length; i++) {
			arr[i] = random.nextInt(range);
		}
		while (sName != 0) {
			int[] tmp = arr.clone();
			System.out.println("请选择排序: 1.冒泡 2.选择 3.插入 4.归并 5.快速 6.堆 7.希尔排序 8.计数排序 9.基数排序  0.退出");
			sName = scanner.nextInt(); // next()方法用来接收控制台输入的字符串
			long t = System.currentTimeMillis();
			switch (sName) {
			case 0:
			default:
				System.exit(1);
				break;
			case 1:
				bubbleSort(tmp);
				break;
			case 2:
				selectionSort(tmp);
				break;
			case 3:
				insertionSort(tmp);
				break;
			case 4:
				mergeSort(tmp);
				break;
			case 5:
				quickSort(tmp);
				break;
			case 6:
				heapSort(tmp);
				break;
			case 7:
				hellSort(tmp);
				break;
			case 8:
				countingSort(tmp, range);
				break;
			case 9:
				radixSort(tmp, range);
				break;
			}
			System.out.println("消耗时间： " + (System.currentTimeMillis() - t) + "ms");
//			print(tmp);
			System.out.println();
		}

	}

	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private static void print(int[] arr) {
		System.out.println("\n开始打印");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "*");
		}
		System.out.println("\n结束打印");
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
		// 显示交换策略
		// for (int i = 1; i < arr.length; i++) {
		// for (int j = i; j > 0; j--) {
		// if (arr[j] < arr[j - 1]) {
		// swap(arr, j - 1, j);
		// } else {
		// break;
		// }
		// }
		// }
		// 非显示交换策略
		int current = 0;
		int j;
		for (int i = 1; i < arr.length; i++) {
			current = arr[i];
			for (j = i; j > 0; j--) {
				if (current < arr[j - 1]) {
					arr[j] = arr[j - 1];
				} else {
					break;
				}
			}
			arr[j] = current;
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
		// 构建最大堆
		buildMaxHeap(arr, lastIndex);

		while (lastIndex > 0) {
			// 堆顶放到最末尾
			swap(arr, 0, lastIndex--);
			// 对除了末尾元素的数组进行堆排序
			percDown(arr, 0, lastIndex);
		}

	}

	// 获取节点左孩子，因为堆按数组排序，位置从0开始(跟二叉堆从1开始算起不同)，所以左孩子位置为2 * i + 1
	private static int leftChild(int parent) {
		return parent * 2 + 1;
	}

	// 构建最大堆
	private static void buildMaxHeap(int[] arr, int lastIndex) {
		for (int i = lastIndex / 2; i >= 0; i--) {
			percDown(arr, i, lastIndex);
		}
	}

	// 下滤法
	private static void percDown(int[] arr, int parent, int lastIndex) {
		// 记录父节点的左右子节点的最大值的位置
		int child = 0;
		// 父节点左孩子不能超过数组范围，循环一次后，父节点变为上次左右子节点的最大值的位置（也就是上次父节点处理完后往下走，即为下滤）
		for (; leftChild(parent) <= lastIndex; parent = child) {
			child = leftChild(parent);
			if (child < lastIndex && arr[child] < arr[child + 1]) {
				// 左节点位置不能为数组最后一个元素，因为这里要左节点和右节点比较取最大值的位置，用于下面和父节点进行比较
				// 左节点比右节点小，位置变为右节点的位置
				child++;
			}
			if (arr[parent] < arr[child]) {
				// 父节点小于子节点的话，交换数据
				swap(arr, parent, child);
			}
		}

	}

	// 希尔排序，插入排序升级版
	private static void hellSort(int[] arr) {

		int length = arr.length;
		int i;
		// 非显示的交换策略
		// for (int step = length / 2; step > 0; step /= 2) {
		// // step为步长,逐步缩小步长
		// for (int index = step; index < length; index++) {
		// // 从位置step到最后一位做步长为step的插入排序
		// int tmp = arr[index];
		// for (i = index; i >= step && arr[i - step] > tmp; i -= step) {
		// // 前面元素大于当前元素(tmp)，将前面元素的值放到当前位置，tmp不用改变，因为它仍然是最小的，可以继续往下比
		// arr[i] = arr[i - step];
		// }
		// // i<step或者i >= step && arr[i - step]<=
		// // tmp的时候，退出循环，此时arr[i]为未交换值，将tmp赋给它
		// arr[i] = tmp;
		// }
		// }
		// 显示交换策略
		for (int step = length / 2; step > 0; step /= 2) {
			// step为步长,逐步缩小步长
			for (int index = step; index < length; index++) {
				// 从位置step到最后一位做步长为step的插入排序
				for (i = index; i >= step && arr[i - step] > arr[i]; i -= step) {
					swap(arr, i, i - step);
				}
			}
		}

	}
	
	// 计数排序
	private static void countingSort(int[] arr,int max) {
		int[] bucket = new int[max+1];
		for (int i = 0; i < arr.length; i++) {
			bucket[arr[i]] += 1;
		}
		int index=0;
		for (int i = 0; i < bucket.length; i++) {
			if (bucket[i] >0 ) {
				for (int j = 0; j < bucket[i]; j++) {
					arr[index++]=i;
				}
			}
		}
	}

	// 计数排序
	private static void countingSort(int[] arr, int max) {
		// 准备最大元素值个桶
		int[] bucket = new int[max + 1];
		for (int i = 0; i < arr.length; i++) {
			// 元素放入桶中，桶中记录元素的个数
			bucket[arr[i]] += 1;
		}
		int index = 0;
		for (int i = 0; i < bucket.length; i++) {
			for (int j = 0; j < bucket[i]; j++) {
				// 桶的元素依次倒出
				arr[index++] = i;
			}
		}
	}

	// 基数排序
	private static void radixSort(int[] arr, int max) {

		// 位数
		int bit = 1;

		if (max == 0) {
			return;
		}

		// 计算位数
		while ((max/=10)!=0) {
			bit++;
		}

		// 数组元素为10进制数，准备0~9十个桶
		Queue<Integer>[] bucket = new Queue[10];

		for (int i = 1; i <= bit; i++) {
			for (int j = 0; j < arr.length; j++) {
				
				// 计算当前位上的值
				int s=arr[j];
				s/=Math.pow(10, i-1);
				s%=10;
				
				if (bucket[s] == null) {
					bucket[s] = new LinkedList<>();
				}
				bucket[s].add(arr[j]);
			}
			// 倒出
			int index = 0;
			for (int j = 0; j < bucket.length; j++) {
				while (bucket[j] != null && bucket[j].size() != 0) {
					arr[index++] = bucket[j].poll();
				}
			}
		}
	}

}

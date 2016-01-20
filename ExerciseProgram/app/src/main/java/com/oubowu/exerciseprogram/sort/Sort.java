package com.oubowu.exerciseprogram.sort;

import java.util.LinkedList;
import java.util.Queue;

public class Sort {

	// public static void main(String[] args) {
	//
	// // int[] arr = { 1, 6, 3, 8, 4, 3, 12, 5 };
	// Scanner scanner = new Scanner(System.in);
	// // 参数对象是系统进来的流
	// int sName = -1;
	// int length = 200000;
	// // length = 15;
	// int[] arr = new int[length];
	// Random random = new Random();
	// int range = 10000;
	// for (int i = 0; i < length; i++) {
	// arr[i] = random.nextInt(range);
	// }
	// while (sName != 0) {
	// int[] tmp = arr.clone();
	// System.out.println("请选择排序: 1.冒泡 2.选择 3.插入 4.归并 5.快速 6.堆 7.希尔排序 8.计数排序
	// 9.基数排序 0.退出");
	// sName = scanner.nextInt(); // next()方法用来接收控制台输入的字符串
	// long t = System.currentTimeMillis();
	// switch (sName) {
	// case 0:
	// default:
	// System.exit(1);
	// break;
	// case 1:
	// bubbleSort(tmp);
	// break;
	// case 2:
	// selectionSort(tmp);
	// break;
	// case 3:
	// insertionSort(tmp);
	// break;
	// case 4:
	// mergeSort(tmp);
	// break;
	// case 5:
	// quickSort(tmp);
	// break;
	// case 6:
	// heapSort(tmp);
	// break;
	// case 7:
	// hellSort(tmp);
	// break;
	// case 8:
	// countingSort(tmp, range);
	// break;
	// case 9:
	// radixSort(tmp, range);
	// // RadixSort sort=new RadixSort();
	// // sort.radixSort(tmp);
	// break;
	// }
	// System.out.println("消耗时间： " + (System.currentTimeMillis() - t) + "ms");
	// // print(tmp);
	// System.out.println();
	// }
	//
	// }

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
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
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
		while ((max /= 10) != 0) {
			bit++;
		}

		// 数组元素为10进制数，准备0~9十个桶
		Queue<Integer>[] bucket = new Queue[10];

		for (int i = 1; i <= bit; i++) {
			for (int j = 0; j < arr.length; j++) {

				// 计算当前位上的值
				int s = arr[j];
				s /= Math.pow(10, i - 1);
				s %= 10;

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

	// 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离可以不超过k，
	// 并且k相对于数组来说比较小。请选择一个合适的排序算法针对这个数据进行排序。
	// [2,1,4,3,6,5,8,7,10,9],10,2 返回：[1,2,3,4,5,6,7,8,9,10]
	public static int[] sortElement(int[] A, int n, int k) {
		// write code here
		// 使用插入排序 O(N*k)
		// for (int i = 1; i < n; i++) {
		// int tmp = A[i];
		// int j = i;
		// for (; j > 0; j--) {
		// if (tmp < A[j - 1]) {
		// A[j]=A[j-1];
		// }else {
		// break;
		// }
		// }
		// A[j]=tmp;
		// }

		// 最佳答案：使用改进后的堆排序
		for (int i = 0; i < n - 1; i++) {
			// i到i+k-1建小根堆
			// System.out.println("\n" + i + " " + (i + k - 1));
			buildMinHeap(A, i, i + k - 1, i);
			// print(A);
		}

		return A;
	}

	// 构建小根堆
	public static void buildMinHeap(int[] A, int firstIndex, int lastIndex, int offset) {
		lastIndex = lastIndex >= A.length ? A.length - 1 : lastIndex;
		for (int i = (firstIndex + lastIndex) / 2; i >= firstIndex; i--) {
			percDownToMin(A, i, lastIndex, offset);
		}
	}

	// 下滤法
	public static void percDownToMin(int[] A, int parent, int lastIndex, int offset) {

		// System.out.println("下滤法：" + parent + " " + lastIndex + " " + offset);

		int rParent = parent - offset;
		int rLastIndex = lastIndex - offset;
		int rChild;
		int child;

		for (; leftChild(rParent) <= rLastIndex; rParent = rChild) {
			rChild = leftChild(rParent);
			child = rChild + offset;
			if (child < lastIndex && A[child] > A[child + 1]) {
				child++;
			}
			if (A[parent] > A[child]) {
				swap(A, parent, child);
			}
			parent = child;
		}

	}

	// 请设计一个高效算法，判断数组中是否有重复值。必须保证额外空间复杂度为O(1)。给定一个int数组A及它的大小n，请返回它是否有重复值。
	// 使用非递归的堆排序实现，递归的话需要用到函数栈空间复杂度为0(logN)
	public static boolean checkDuplicate(int[] a, int n) {
		// write code here
		heapSort(a);

		// 排好序后，若相邻的数相等即有重复着，返回true
		for (int i = 0; i < n - 1; i++) {
			if (a[i] == a[i + 1]) {
				return true;
			}
		}

		return false;
	}

	// 有两个从小到大排序以后的数组A和B，其中A的末端有足够的缓冲空容纳B。请编写一个方法，将B合并入A并排序。
	// 给定两个有序int数组A和B，A中的缓冲空用0填充，同时给定A和B的真实大小int n和int m，请返回合并后的数组。
	public static int[] mergeAB(int[] A, int[] B, int n, int m) {
		// write code here
		int mergeIndex = n + m - 1;
		int aIndex = n - 1;
		int bIndex = m - 1;

		while (aIndex >= 0 && bIndex >= 0) {
			if (A[aIndex] > B[bIndex]) {
				A[mergeIndex--] = A[aIndex--];
			} else {
				A[mergeIndex--] = B[bIndex--];
			}
		}
		while (aIndex >= 0) {
			A[mergeIndex--] = A[aIndex--];
		}
		while (bIndex >= 0) {
			A[mergeIndex--] = B[bIndex--];
		}
		return A;
	}

	// 有一个只由0，1，2三种元素构成的整数数组，请使用交换、原地排序而不是使用计数进行排序。
	// 给定一个只含0，1，2的整数数组A及它的大小，请返回排序后的数组。保证数组大小小于等于500。
	public int[] sortThreeColor(int[] A, int n) {
		// write code here

		// 类似快排

		// 0区为数组最前的上一个位置
		int zeroIndex = -1;
		// 2区为数组最后的下一个位置
		int twoIndex = n;

		for (int i = 0; i < n; i++) {
			if (A[i] == 0) {
				// 元素为0，与0区下一个位置元素交换
				swap(A, ++zeroIndex, i);
			} else if (A[i] == 2 && i < twoIndex) {
				// i<twoIndex是因为，twoIndex是已经处理过的数据了，再比较就出差错了
				// 元素为2，与2区上一个位置元素交换
				// 因为交换后的元素前面没比较，所以这里i--，下次循环再次比较当前位置
				swap(A, --twoIndex, i--);
			} else if (i >= twoIndex) {
				break;
			}
		}

		return A;
	}

	// 现在有一个行和列都排好序的矩阵，请设计一个高效算法，快速查找矩阵中是否含有值x。
	// 给定一个int矩阵mat，同时给定矩阵大小nxm及待查找的数x，请返回一个bool值，代表矩阵中是否存在x。
	// 所有矩阵中数字及x均为int范围内整数。保证n和m均小于等于1000。
	// 0 1 2 5 ->递增
	// 2 3 4 7 ^
	// 4 4 4 8 | 递减
	// 5 7 7 9
	// 查找方法：从右上开始找，若比它大则说明在下面，行数加一，若比它小则说明在前面，列数减一
	/**
	 * 查找矩阵是否存在某个数
	 * 
	 * @param mat
	 *            矩阵
	 * @param n
	 *            行数
	 * @param m
	 *            列数
	 * @param x
	 *            需要查找的数
	 * @return 布尔值
	 */
	public boolean findX(int[][] mat, int n, int m, int x) {
		// write code here
		int nIndex = 0;
		int mIndex = m - 1;
		int current = 0;
		boolean result = false;
		while (nIndex < n && mIndex >= 0) {
			current = mat[nIndex][mIndex];
			if (current < x) {
				nIndex++;
			} else if (current > x) {
				mIndex--;
			} else {
				return true;
			}
		}
		return result;
	}

	// 对于一个数组，请设计一个高效算法计算需要排序的最短子数组的长度。
	// 1 5 4 3 2 6 7 需要排序的最短子数组为{5,4,3,2} 长度为4
	// PS:这里是针对递增数组
	public int shortestSubsequence(int[] A, int n) {
		// write code here
		int max = A[0];
		int rightIndex = -1;
		for (int i = 1; i < n; i++) {
			if (max > A[i]) {
				// 若是递增数组，max是前面的元素，所以A[i]是要大于等于max的
				// max大于A[i]说明了，这里不符合递增规律，它是需要排序的
				rightIndex = i;
			} else {
				max = A[i];
			}
		}

		int min = A[n - 1];
		int leftIndex = -1;
		for (int i = n - 2; i > 0; i--) {
			if (min >= A[i]) {
				min = A[i];
			} else {
				// 若是递增数组，min是后面的元素，所以min是要大于等于A[i]的
				// 这里min<A[i]说明不符合递增的规律，它是需要排序的
				leftIndex = i;
			}
		}

		System.out.println(leftIndex + " " + rightIndex);
		if (rightIndex == -1 && leftIndex == -1) {
			// rightIndex == -1 && leftIndex == -1说明已经是排好序的了，所以长度为0
			return 0;
		}
		return rightIndex - leftIndex + 1;
	}

	// 有一个整形数组A，请设计一个复杂度为O(n)的算法，算出排序后相邻两数的最大差值。
	// 给定一个int数组A和A的大小n，请返回最大的差值。保证数组元素多于1个。
	// 1 2 3 4 7 8 9 最大差值为7-4=3
	// 使用桶的思想将 1 2 3 4 7 8 9这7个数，[1,9)分为7个等量区间作为桶,
	// 考虑相邻桶的差值(前面一个桶的最大值与后面一个桶的最小值的差值)
	public int maxGap(int[] A, int n) {

		if (A == null || A.length < 2) {
			return 0;
		}

		// write code here
		// 找出最大最小值
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			max = Math.max(max, A[i]);
			min = Math.min(min, A[i]);
		}
		if (max == min) {
			return 0;
		}

		// 桶中是否存有数据
		boolean[] hasNum = new boolean[n + 1];
		// 桶中最大值
		int[] bucketMaxValue = new int[n + 1];
		// 桶中最小值
		int[] bucketMinValue = new int[n + 1];
		// 使用long类型是为了防止相乘时溢出
		long ln = n;
		// 桶的等量区间为 (max-min)/n;算出桶号 (A[i]-min)/((max-min)/n)
		for (int i = 0; i < n; i++) {
			int bucketNum = (int) ((A[i] - min) * ln / (max - min));
			// System.out.println(A[i] + "的桶号：" + bucketNum);
			// 通过桶的布尔值判断是否存过数据，没存过的话直接存数据，有的话存已存的数据和现在的最大值
			bucketMaxValue[bucketNum] = hasNum[bucketNum] ? Math.max(bucketMaxValue[bucketNum], A[i]) : A[i];
			// 通过桶的布尔值判断是否存过数据，没存过的话直接存数据，有的话存已存的数据和现在的最小值
			bucketMinValue[bucketNum] = hasNum[bucketNum] ? Math.min(bucketMinValue[bucketNum], A[i]) : A[i];
			// 上面操作存了最大最小值，布尔值设为true
			hasNum[bucketNum] = true;
		}

		// v1是前面桶的最大值，v2是后面桶的最小值,offset是差值,pp是后面桶的步长
		int v1 = 0, v2 = 0, offset = 0, pp = 1;
		for (int i = 0; i < bucketMinValue.length - pp; i++) {
			if (hasNum[i]) {
				v1 = bucketMaxValue[i];
			} else {
				// v1为空桶的话直接跳过不处理
				continue;
			}
			if (hasNum[i + pp]) {
				v2 = bucketMinValue[i + pp];
				pp = 1;
			} else {
				// 有空桶的情况，这个时候i--使得循环v1还是在这个位置上,pp+=使得v2跨多一步去获取数据
				pp++;
				i--;
				continue;
			}
			System.out.print(v2 + "-" + v1 + " 最大差值：");
			offset = v2 - v1 > offset ? v2 - v1 : offset;
			System.out.println(offset);
		}
		return offset;
	}

	public static void main(String[] args) {

		Sort sort = new Sort();

		// int[] A = { 2, 1, 4, 3, 6, 5, 8, 7, 10, 9 };
		// print(sortElement(A, A.length, 2));

		// int[] A = { 3, 2, 1, 6, 5, 4, 9, 8, 7 };
		// print(sortElement(A, A.length, 3));
		// System.out.println(checkDuplicate(A, A.length));
		// int[] A = { 2, 4, 6, 7, 0, 0, 0, 0 };
		// int[] B = { 1, 3, 5 };
		// mergeAB(A, B, 4, 3);

		// int[] A = { 1, 1, 0, 0, 2, 1, 1, 0 };
		// sort.sortThreeColor(A, A.length);
		// print(A);

		// [[1,2,3],[4,5,6],[7,8,9]],3,3,10
		// int[][] A = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		// System.out.println("找得到10吗: " + sort.findX(A, 3, 3, 10));
		// System.out.println("找得到1吗: " + sort.findX(A, 3, 3, 1));

		// int[] A = { 1, 5, 4, 3, 2, 6, 7 };
		// int[] A = { 1, 2, 3, 3, 8, 9 };
		// System.out.println(sort.shortestSubsequence(A, A.length));
		int[] A = { 7, 9, 3, 4, 2, 1, 8 };
		System.out.println(sort.maxGap(A, A.length));
	}

}

package algorithm;

public class Sort {

  /**
   * @param args
   */
  public static void main(String[] args) {
    int[] ss = {4, 5, 7, 1, 34, 67, 0, 123};
    quick_sort(ss, 0, ss.length - 1);
    for (int s : ss) {
      System.out.println(s);
    }
  }

  // 快速排序：挖坑填数+分治法
  static void quick_sort(int s[], int l, int r) {
    if (l < r) {
      // Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 ，将中间数作为基准数
      int i = l, j = r, x = s[l];
      while (i < j) {
        while (i < j && s[j] >= x)
          // 从右向左找第一个小于x的数
          j--;
        if (i < j)
          s[i++] = s[j];

        while (i < j && s[i] < x)
          // 从左向右找第一个大于等于x的数
          i++;
        if (i < j)
          s[j--] = s[i];
      }
      s[i] = x;
      quick_sort(s, l, i - 1); // 递归调用
      quick_sort(s, i + 1, r);
    }
  }

}



/**
 * 二分查找
 *
 */
class BinarySearch {
  private int rCount = 0;
  private int lCount = 0;

  /**
   * 获取递归的次数
   * 
   * @return
   */
  public int getrCount() {
    return rCount;
  }

  /**
   * 获取循环的次数
   * 
   * @return
   */
  public int getlCount() {
    return lCount;
  }

  /**
   * 执行递归二分查找，返回第一次出现该值的位置
   * 
   * @param sortedData 已排序的数组
   * @param start 开始位置
   * @param end 结束位置
   * @param findValue 需要找的值
   * @return 值在数组中的位置，从0开始。找不到返回-1
   */
  public int searchRecursive(int[] sortedData, int start, int end, int findValue) {
    rCount++;
    if (start <= end) {
      // 中间位置
      int middle = (start + end) >> 1; // 相当于(start+end)/2
      // 中值
      int middleValue = sortedData[middle];

      if (findValue == middleValue) {
        // 等于中值直接返回
        return middle;
      } else if (findValue < middleValue) {
        // 小于中值时在中值前面找
        return searchRecursive(sortedData, start, middle - 1, findValue);
      } else {
        // 大于中值在中值后面找
        return searchRecursive(sortedData, middle + 1, end, findValue);
      }
    } else {
      // 找不到
      return -1;
    }
  }

  /**
   * 循环二分查找，返回第一次出现该值的位置
   * 
   * @param sortedData 已排序的数组
   * @param findValue 需要找的值
   * @return 值在数组中的位置，从0开始。找不到返回-1
   */
  public int searchLoop(int[] sortedData, int findValue) {
    int start = 0;
    int end = sortedData.length - 1;

    while (start <= end) {
      lCount++;
      // 中间位置
      int middle = (start + end) >> 1; // 相当于(start+end)/2
      // 中值
      int middleValue = sortedData[middle];

      if (findValue == middleValue) {
        // 等于中值直接返回
        return middle;
      } else if (findValue < middleValue) {
        // 小于中值时在中值前面找
        end = middle - 1;
      } else {
        // 大于中值在中值后面找
        start = middle + 1;
      }
    }
    // 找不到
    return -1;
  }
}

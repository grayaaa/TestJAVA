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

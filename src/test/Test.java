package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
//		String str = "Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTVjAxV2JETlhhMUpUVmpBeFYySkVUbGhoTVVwVVZtcEJlRll5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVmxaM1ZsWmFjVkZ0UmxSTmJFcEpWbTEwYTFkSFNrZGpTRUpYWVRGd2FGcFdXbUZrUlRGVlZXeFNUbUY2UlRGV2EyUXdZekpHYzFOdVVtaFNlbXhXVm1wT1QwMHhjRlpYYlVaclVqQTFSMWRyV25kV01ERkZVbFJHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNVlY1VFZSU1ZrMXJjRWxhU0hCSFZqRmFSbUl6WkZkaGExcG9WakJhVDJOdFJraGhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJGWmhZMnhXY1ZGVVJsTk5WMUo1VmpKNFQxWlhTbFpYVkVwV1lrWktTRlpxUm1GU2JVbDZXa1prYUdFeGNHOVdha0poVkRKT2RGSnJhR2hTYXpWeldXeG9iMWRHV25STldHUlZUVlpHTTFSVmFHOWhiRXB6WTBac1dtSkdXbWhaTVZwaFpFZFNTRkpyTlZOaVJtOTNWMnhXYjJFeFdYZE5WVlpUWVRGd1YxbHJXa3RUUmxweFVtMUdVMkpWYkRaWGExcHJZVWRGZUdOSE9WZGhhMHBvVmtSS1QyUkdTbkpoUjJoVFlYcFdlbGRYZUc5aU1XUkhWMjVTVGxkSFVsWlVWbHBIVFRGU1ZtRkhPV2hpUlhCNldUQmFjMWR0U2toaFJsSlhUVVp3VkZacVJtdGtWbkJHVGxaT2FWSnRPVE5XTW5oWFdWWlJlRmRzYUZSaVJuQlpWbXRXZDFZeGJISlhhM1JUVW14d2VGVXlkR3RoYlVwV1ZtcGFXbFpXY0doWlZXUkdaVWRPU0U5V1pHaGhNSEJ2Vm10U1MxUXlVa2RUYmtwaFVtMW9jRlpxVG05a2JGcEhWbTA1VWsxWFVucFdNV2h2VjBkS1JrNVdVbFZXTTJoSVZHeGFWMlJIVWtoa1IyaHBVbGhCZDFac1pEUmpNV1IwVTJ0b2FGSnNTbGhVVlZwM1ZrWmFjVk5yWkZOaVJrcDZWa2N4YzFVeVNuSlRiVVpYVFc1b1dGbHFTa1psUm1SWldrVTFWMVpzY0ZWWFZsSkhaREZaZUdKSVNsaGhNMUpVVlcxNGQyVkdWbGRoUnpsb1RWWndlbFl5Y0VkV01ERjFZVWRvV2xaWFVrZGFWV1JQVWpKS1IyRkhhRTVXYmtKMlZtMTBVMU14VW5SV2EyUnFVbGQ0Vmxsc1pHOVdSbEpZVGxjNVYxWnNjRWhYVkU1dllWVXhXRlZ1Y0ZkTlYyaDJWakJrUzFkV1ZuSlBWbHBwVmtWVmQxWnFRbUZaVm1SSVZXdG9hMUp0VWs5V2FrWkxVMnhrVjFadFJtcE5WMUl3VlRKMGExZEhTbGhoUjBaVlZucFdkbFl3V25KbFJtUnlXa1prVjJFelFqWldhMlI2VFZaa1IxTnNXbXBTVjNoWVdXeG9RMVJHVW5KWGJFcHNVbTFTZWxsVldsTmhSVEZ6VTI1b1YxWjZSVEJhUkVaclVqSktTVlJ0YUZOaGVsWlFWa1phWVdReVZrZFdXR3hyVWtWS1dGUldXbmRsVm10M1YyNWtXRkl3VmpSWk1GSlBWakpHY2xkcmVGZGhhM0JRVlRGa1MxSXhjRWRhUms1WFYwVktNbFp0TVRCVk1VMTRWVmhzVlZkSGVGWlpWRVozWWpGV2NWUnJUbGRTYlhoYVdUQmFhMkV3TVZkalJFSmFUVVpaZDFsV1ZYaGpNVTUxWTBaa1RtRnNXbFZXYTJRMFlURk9SMVp1VGxoaVJscFlWRlJHUzA1c1draGxSMFpYVFd4S1NWWlhkRzloTVVsNVlVaENWbUpIYUVOYVJFWmhZekZ3UlZWdGNFNVdNVWwzVmxSS01HSXlSa2RUYms1VVlrZG9WbFpzV25kTk1WcHlWMjFHYWxacmNERlhhMXBQVmpKRmVHTkVWbGRTUlZweVdYcEdWbVF3TVVsaVIyaFRZbGhvV1ZkWGVHOVJNVkpIVlc1S1dHSkZjSE5WYlRGVFpXeHNWbGRzVG1oU1ZFWjZWVEkxYjFZeFdqWlNWRUpoVWtWYWNsVnFTa3RUVmxKMFlVWk9iR0pZYURaV01XUXdXVmRSZVZaclpGZGliRXBQVm14a1UxWnNVbGhrU0dSVFRWWnNOVnBWYUd0WFIwcEhZMFpvV2sxSGFFeFdha1poVW14a2NtVkdaRTVXYmtKSlYxUkplRk14U1hoalJXaHBVbTFvVkZac2FFTlRNVnAwVFZSQ1ZrMVZiRFZWYkdodlYwWmtTR1ZHV2xwV1JWb3pXVlZhVjJOV1RuUlBWbVJUWWtWd1dsWkhlR3BPVmxsNFYyNVNWbUpIYUZoV2FrNU9UVlphV0dNemFGaFNiRm94V1RCYWExUnNXWGxoUkVwWFRWWndhRlY2UmtwbFJsSjFWbXhLYVZKc2NGbFdSbEpIVXpBMWMxZHJaRlpoTWxKWFZGWmFkMDFHVm5Sa1J6bFdVbXhzTlZsVmFFTldiVXBJWVVWT1lWSkZXbWhaZWtaM1VsWldkR05GTlZkTlZXd3pWbXhTUzAxSFJYaGFSV2hVWWtkb2IxVnFRbUZXYkZwMFpVaGtUazFZUWxsYVZXaExZa1paZUZkcmJHRlNWMUYzVmxSS1JtVnNSbGxhUm1ocFVteHdiMWRXVWt0U01WbDRXa2hXVkdKWGVITlpWRVozVjFaa1dHVkhPVkpOVmtwSVZsYzFTMWRIU2taalNFNVdZbFJHVkZwWGVITldiR1J6Vkcxb1YyRXpRWGhXVm1RMFpERlplVk5zYkZaaVIyaG9WV3RXWVZsV2NGWmFSWFJVVm1zMWVsbFZaRzlVYXpGV1kwWmFWMkpIVGpSVWEyUlNaVlphY2xwR1pGaFNNMmg1Vmxkd1ExbFhUa2RXYmxKc1UwZFNjMWxyV2xkT1ZuQldZVWQwV0ZJd2NFaFpNRnB2VjJzeFNHRkZlRmRoYTFwb1ZXMTRhMk50VmtkYVJUVlhZbXRLU2xZeFVrcGxSazE0VTFob2FsSlhhSEJWYlRGdlZrWmFjMkZGVGxSTlZuQXdWRlpTUTFack1WWk5WRkpYWWtkb2RsWXdXbXRUUjBaSVlVWmFUbEp1UW05V2JURTBZekpPYzFwSVNtdFNNMEpVV1d0b1EwNUdXbFZUV0dSUFZqQndTVlV5ZEc5V2JVcElaVWRvVjJKSFVrOVVWbHB6VmpGYVdXRkdhRk5pUm5BMlYxWldZV0V4VW5OWFdHeG9Va1Z3V0ZSV1duZGhSbFkyVW10d2JGSnNTakZXYlhoTFlWWktjMk5HYkZkU2JFcElWWHBCTVdNeFpISmFSbHBvVFd4S1dGWkdXbUZqTURWWFZtNVNhMUl3V2xkVmJYaHpUbFpzVm1GRlRsZGlWWEJKV1ZWV1QxbFdTa1pYYmtwWFlXdGFhRnBGVlRWV01WcHlUbFprYVdFd1dYcFdiWGhxWkRBeFYxUllhRlpYUjJoWldXMXpNVlpXYkhKYVJ6bFhZa1p3TUZwVmFHdFVhekZYWWtST1YwMXFWa3haYTFwTFpFWldkV0pIUmxOV01VbDZWMVphWVZsWFRuUlVhMXBwVW0xU2NGVnFSa1prTVdSWFZXdDBVMDFXYkRSV1J6VlhWbTFLUmxOc2FGWmlSa3BZVmpGYVlWSXhiRFpTYld4T1ZqRktTVll5ZEdGaE1XUklVMnRhYWxORk5WZFpiRkpIVmtad1dHVklUbGRpUjFKNlZrY3hiMVV4V2taWGFscFhWa1ZyZUZscVJscGxSbVJaWTBaYWFWSXlhRnBXYlRFMFpERnNWMk5HV2xoaVdGSnlWbTEwZDJWc1duUk5XRTVYVFZWc05sbFZVbUZXTURGWVZWaGtXRlp0VWxOYVZscGhZMnh3UjFwSGJHbFNXRUpSVm0weE5HRXhWWGhYV0doV1lrZG9jbFV3WkZOWFJsSlhWMjVrVGxKdGRETlhhMVpyVjBaSmQyTkZhRnBOUm5CMlZqSnplRk5HVm5WWGJHUk9ZV3RhU0Zkc1dtRldNazV6WTBWb1UySkhVazlVVnpGdlUyeFplRlZyY0d4U2F6RTBWVEZvYzFVeVJYbFZiVGxXWWxob1RGWnJXbUZqTWtaR1ZHeFNUbFp1UVhkV1JscFRVVEZhY2sxV1drNVdSa3BZV1d0a2IyUnNXWGRYYlhSVVVqQmFTRmxyV25kaFZtUklZVWM1VjJKWVFraFpla3BPWlVkT1JtRkdRbGRpVmtwVlYxZDRiMkl4YkZkYVJsWlNZbFZhYjFSWGRGZE5NVkpYVjIxMGFHSkZOVWxaVlZwclZqSktkVkZyWkdGU1JWcDZWbXBHWVZkWFJrZGhSazVwVW01Qk1sWXhXbGRaVjBWNFZXNU9XRmRIZUc5VmExWjNWMFpzV0dSRmRHcGlSbGt5VlcweE1GWXdNVmRqUkVaWFlsaG9XRmxXV2s5U2JFNXpZMFprVjFKVmNGbFdXSEJIVkRGWmVGcElWbE5pUlRWd1ZteGFkMkZHV25STlNHaFdUVlZzTkZaWE5VOVhSMHBXVjJ4a1ZtSllhRE5VVlZwelZteGtjMVJzYUdsV1dFSkpWMVJDVjA1R1duSk5XRlpvVWpKb1lWcFhkR0ZOTVZaelYyeGthMUl3TlVkVWJGcHJWR3hhV0dRemNGZGlXR2hVVlhwQmVGTkdUbGxpUms1b1RXeEtWbGRYTVhwTlZscFhZa2hPVjJKVldsaFphMXAzVFVad1ZtRkhkRlZoZWtaWVdUQm9jMVl3TVhGU2EyaGFWak5vYUZreWN6RlhWMHBIWVVkb1RsZEZTbEpXTVZwaFdWWk5lVlJ1VWxWaE1WcFpXV3RrVTJJeFVsaGpla0pPVW0xU1dsa3dWbXRXTWtwWFYyeG9WMUo2VmxoV2EyUkxVakZPZFZGc1pHbFNia0Y2Vm1wR1lWbFdTWGhpUkZwVFlsZDRUMVpxUmt0VFZsbDVaRWRHYUUxWFVrbFZNblJoWVd4T1JrNVdaRnBpUmtwSVZtdGFkMWRIVmtsVWJHUnBVakZLTmxaclkzaGlNVlY0VjJ0YVdHRnNjRmhXYTFaeVpVWnNjVkpzY0d4U2JWSjRWako0UzJGSFNrWmpSMmhZVm0xUmQxZFdaRmRqTVdSMVVteE9hVkl4U25oV1JscHJWVEpXYzJKR1dtRlNlbXhXV1Zod1IxWXhhM2RhUldSWFRXdHdTVlpIY0ZOV1YwVjVWV3M1WVZKRlJqUlZNV1JIVWpKR1IyRkdUazVOYldoU1ZtMHdlRTVIUlhoV1dHaFlWMGRvVjFsclpHOWpiRlYzV2taT1YxSnNTbGhXTW5Rd1lrZEtSMk5HYkdGU1ZsVXhWakJhU21ReVRrWmhSbkJPVW01Q01sWnFTbnBsUms1SVVtdGFiRkp0VWs5WmJURnZZakZhY1ZGdFJsZE5helY2V1RCV2IxVXlTa2hWYkdoVlZteGFNMVpYZUdGak1WWnlWR3hrYUdWc1dsbFdha1p2WWpKR2MxTnNhR2hTZW14WFdWZDBkMlJzV2tWU2JGcHJUVlp3ZVZwRlZURmhWa3AxVVZoa1dHSkdXbkZVYkdSR1pEQXhWMWR0YkZOU2JIQllWMWQwYTJJeVVuTmFSbVJZWVROU1dWVnRlR0ZsYkZwMFpVaGthRlp0VWtoVk1XaDNWMFphYzFkdGFGZGhhM0JRVm1wR1UyUldWbk5SYkdScFZtdHdWbFl4WkRCV01sRjRXa2hPV0dFeVVsbFpiR2hEVlVaYWRHVklaR3hpUmxZMVZHeFZOV0ZIU2taalJteGFWbFp3ZWxacVNrWmxSbHBaWVVkR1UwMHlhRFpXYlhCSFdWWmtXRkpyWkdoU2F6VndWVzB3TlU1R1dYaFZhMDVhVmpCV05GWlhOVTlYUm1SSVpVYzVWbUV4V2pOV01GcHpWMGRTUm1SSGNHbFNiR3Q1VmxjeE1FMUhSblJTYWxwWFlrZG9XVmxVUm5kaFJteFhWMnQwYWsxck5VaFphMXB2VmpBd2VGTnFTbGRXYlU0MFZtcEtUbVZHY0VsVGJVWlRZbFpLZDFadGVHRmtNa1pIVjI1U2FsSlhVbFZVVmxVeFYwWlplV1ZIT1doTlZXOHlXV3RqTlZaV1duTlhhazVWVmxad2FGWXdaRTlPYkZwellVZHNVMkpyU2tsV2EyUTBWakZWZUZkc2FGUmlSM2h2VldwS2IxZEdiSEpYYm1SV1VteHNORmRyVm10Vk1ERlhVMjVzVldKR2NISlpWbVJHWkRKT1NGSnNaR2xXUlZsNlZsaHdTMVZ0VmtoVGEyUmhVbTFvV1ZWcVRtOVdiR1JZVFZod1RsWXdOVmhXYlRWVFZHeGFObUpHYUZWV2JWRXdWbTE0VjJSRk1WWmFSMmhUWVROQ05sZFVRbTlqTVZsM1RWaEdVMkV5YUdGV2ExWmhVekZ3VmxkdGRHcGlWWEJKVlcxNFQxWXdNVlppUkZwWFRWWndhRmRXV2xKbFJrNTFWR3hXYVdFelFuaFdWekI0WWpGa1IxWnVUbGRpYlZKWlZXMTRkMU5HV1hsTlZXUllVakJ3V0ZZeWVHOVdhekYxWVVod1dsWXphRXhaTWpGUFUxWkdjMWR0YUdoTk1FbDVWbTF3UjFsWFJYaGFSV2hXWVRKb1ZGbHNhRk5VTVd4WlkwWmtUMkpHY0hwWFdIQkhWa1V4V0ZWcmJGWmlXRUpvVm1wS1MyTnJOVmRhUm5CcFVqSm9NbFpyVm1GWGJWWllWbXRzVldKSFVuQlZha1pLWkRGYVJWSnRkR2xOVmxZMFZqSjBZVmRIU2xaWGJHaFhZbFJHVTFSVldsZGpWa3B6WTBkNFYyRjZWalpYVjNScllqRlZkMDFZVW1oU01taFlWVzB4VTAweFZuRlNiRTVUVFZad01WWlhNWGRVYXpGMFlVWmFWMkpVUVhoVlZFWlBVakZrV1dKSGVGTk5SbkJYVjFkMFlXUXlWbk5pUmxaVlltczFXRlJYZEhkVFZscFhZVWQwVjJKVmNGWlZiWFEwVm0xS1dWUnFVbFpOVm5BelZXcEdhMk14Y0VaT1ZUVnBWakpvVTFadE1UQlpWbGw0WWtaa1ZHSnNTbFZaVkVaM1ZERlZkMXBHVGxkTlZuQldWVzE0YTFReVNrZFhibXhXWWtkUk1GWkhjM2hTTVVwMVZHeFdWMUpWY0ZSVk1uaFRWREZLVWtwVVRrVktWRTVG";
//		BaseEncoding ss = BaseEncoding.base64();
//		System.out.println(ss.decode(str));
//
//		// // TODO Auto-generated method stub
//		// String temp = "{localip:1.1.1.1,remoteip:1.1.1.2,mos_hastate:ok}";
//		// String temp2 = "[{localip:1.1.1.1,remoteip:1.1.1.2,mos_hastate:ok}]";
//		// // Map map = JsonUtil.getMap4Json(temp);
//		// Gson gson = new Gson();
//		// System.out.println(gson.toJson(temp));
//
//		List<User> listuser = new ArrayList<User>();
//		User user1 = new User("a1", "0");
//		User user2 = new User("a2", "2");
//		User user3 = new User("a3", "19");
//		listuser.add(user1);
//		listuser.add(user2);
//		listuser.add(user3);
//		listuser.add(user1);
//
//		Collections.sort(listuser, new Comparator<User>() {
//			public int compare(User arg0, User arg1) {
//				// int i = arg0.getAge().compareTo(arg1.getAge());
//				// return i;
//				int a = Integer.valueOf(arg0.getAge());
//				int b = Integer.valueOf(arg1.getAge());
//				if (a > b) {
//					return 1;
//				} else if (a == b) {
//					return 0;
//				} else {
//					return -1;
//				}
//			}
//		});
//
//		for (User user : listuser) {
//			System.out.println(user.getName());
//		}


        List<List<String>> tmp = new ArrayList<List<String>>();

        List<String> list1 = new ArrayList<String>();

        list1.add("list1");
        list1.add("2.3658");
        list1.add("19");
        list1.add("1520.00");

        List<String> list2 = new ArrayList<String>();
        list2.add("list2");
        list2.add("5.3658");
        list2.add("20");
        list2.add("2563.00");

        List<String> list3 = new ArrayList<String>();
        list3.add("list3");
        list3.add("5.0658");
        list3.add("17.235");
        list3.add("2563.50");

        tmp.add(list1);
        tmp.add(list2);
        tmp.add(list3);

        Collections.sort(tmp, new MyListComparator(2));

        System.out.println(tmp.toString());



        String z= "ipaD";
        System.out.println(z.toUpperCase().charAt(0)+z.substring(1, z.length()));



        if(1==1){
            System.out.println("11111111111111111");
        }else if(2==2){
            System.out.println("22222222222222222");
        }
    }
}

class MyListComparator implements Comparator {
    private int index;

    MyListComparator(int index) {
        this.index = index;
    }

    public int compare(Object o1, Object o2) {
        List<String> list1 = (List<String>) o1;
        List<String> list2 = (List<String>) o2;

        BigDecimal data1 = new BigDecimal(Double.parseDouble(list1.get(index)));
        BigDecimal data2 = new BigDecimal(Double.parseDouble(list2.get(index)));

        int s = data1.compareTo(data2);
        if (s == 1) return -1;
        if (s == -1) return 1;
        return 0;
    }
}

package cc.mrbird.common.util;

import cc.mrbird.system.domain.UserEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareUtils {

    public CompareUtils() {
    }
    /**
     * 比较单个值
     * @param objA
     * @return
     */
    public DifferenceAttr compareValue(Object objA) {
       DifferenceAttr differAttr = new DifferenceAttr();
        try{
            List<String> differenceAttrs = new ArrayList<String>();
            List<DifferenceAttr> childDifferenceAttr = new ArrayList<DifferenceAttr>();
            Class<?> clazzA = objA.getClass();
            Method[] methods = clazzA.getDeclaredMethods();
            Object result = null;
            for(Method method:methods) {
                if(method.getName().startsWith("get")) {
                    result = method.invoke(objA, null);
                    if(result==null) {
                        continue;
                    }
                    if(result instanceof List) {
                        List<Object> childList = (List)result;
                        for(Object object:childList) {
                            childDifferenceAttr.add(compareValue(object));
                        }
                    }else {
                        String nameTrim = method.getName().substring(3);
                        nameTrim = nameTrim.substring(0,1).toLowerCase()+nameTrim.substring(1);
                        differenceAttrs.add(nameTrim);
                    }
                }
            }
            differAttr.setDifferenceAttrs(differenceAttrs);
            differAttr.setChildrenDifference(childDifferenceAttr);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return differAttr;
    }
    /**
     * 两个对象值的对比（objA为对比）
     * @param objA
     * @param objB
     * @return
     */
    public DifferenceAttr compareValue(Object objA,Object objB) {
        DifferenceAttr differenceAttr = new DifferenceAttr();
        //值都为空，则返回
        if(objA==null&&objB==null) {
            return null;
        }
        //objA不为空，objB为空,则把返回objA所有字段
        if(objA!=null&&objB==null) {
            return compareValue(objA);
        }
        //当前类差异集合
        List<String> differenceAttrs = new ArrayList<String>();
        //子集合查询集合
        List<DifferenceAttr> childrenDifference = new ArrayList<DifferenceAttr>();

        try {
            Class<?> clazzA = objA.getClass();
            Class<?> clazzB = objB.getClass();
            Method[] methods = clazzA.getDeclaredMethods();
            Field[] f =clazzA.getDeclaredFields();
            Map<String, String> map = new HashMap<>();
            for(Field field : f){
                map.put(("get" + field.getName()).toLowerCase(), field.getName().toString());
            }
            Object resultA = null;
            Object resultB = null;
            Method methodB = null;
            for(Method method:methods) {
                if(method.getName().startsWith("get")) {
                    methodB = clazzB.getMethod(method.getName(), null);
                    resultB = methodB.invoke(objB, null);
                    resultA = method.invoke(objA, null);
                    if(resultA==null&&resultB==null) {
                        continue;
                    }

                    if(resultA==null&&resultB!=null) {
                        String field = map.get(method.getName().toLowerCase());
                        resultA = resultB;
                        invokeSet(objA, field, resultA);

//                        Field f1 = clazzA.getDeclaredField(field);
//                        f1.setAccessible(true);
//                        f1.set(field, resultA);
//                        differenceAttrs.add(method.getName());
                    }else if(resultA!=null&&resultB==null) {
                        differenceAttrs.add(method.getName());
                    }else {

                        if(!(resultA instanceof List)) {
                            if(!resultA.equals(resultB)) {
                                String field = map.get(method.getName().toLowerCase());
                                resultA = resultB;
                                invokeSet(objA,field,resultA);
//                                Field f1 = clazzA.getDeclaredField(field);
////                                f1.setAccessible(true);
////                                f1.set(field, resultA);
                                String nameTrim = method.getName().substring(3);
                                nameTrim = nameTrim.substring(0,1).toLowerCase()+nameTrim.substring(1);
                                differenceAttrs.add(nameTrim);
                            }
                        }else {
                            List<Object> listA = (List<Object>)resultA;
                            List<Object> listB = (List<Object>)resultB;

                            for(int i=0;i<listA.size();i++) {
                                DifferenceAttr childAttr = null;
                                if(i>=listB.size()) {
                                    childAttr = compareValue(listA.get(i), null);
                                }else {
                                    childAttr = compareValue(listA.get(i), listB.get(i));
                                }
                                childrenDifference.add(childAttr);
                            }
                        }
                    }
                    differenceAttr.setChildrenDifference(childrenDifference);
                    differenceAttr.setDifferenceAttrs(differenceAttrs);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return differenceAttr;
    }

    /**
     * 执行set方法
     *
     * @param o 执行对象
     * @param fieldName 属性
     * @param value 值
     */
    public static void invokeSet(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);
        try {
            method.invoke(o, new Object[] { value });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java反射bean的set方法
     *
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CompareUtils attrCompare = new CompareUtils();

        UserEntity object1 = new UserEntity();

        object1.setFid("111111");
        object1.setFnumber("Y");
        object1.setFname("Y");
        object1.setFcell("13111111111");

//        List<UserEntity> childList1 = new ArrayList<UserEntity>();
//        UserEntity child1_1 = new UserEntity();
//        child1_1.setFid("测试地址1_1");
//        child1_1.setfPersonid("测试姓名");
//        child1_1.setfNumber("测试值1_1");
//
//        UserEntity child1_2 = new UserEntity();
//        child1_1.setFid("测试地址1_2");
//        child1_1.setfPersonid("测试姓名2");
//        child1_1.setfNumber("测试值1_2");
//
//        childList1.add(child1_1);
//        childList1.add(child1_2);
//        object1.setList(childList1);

        UserEntity object2 = new UserEntity();
        object2.setFid("111111");
        object2.setFnumber("333");
        object2.setFcell("13111111111");
        object2.setFpersonid("这是个测试");
//        List<ChildObject> childList2 = new ArrayList<ChildObject>();
//        ChildObject child2_1 = new ChildObject();
//        child2_1.setAddress("测试地址2_1");
//        child2_1.setChildName("测试姓名");
//        child2_1.setChildValue("测试值2_1");
//
//        ChildObject child2_2 = new ChildObject();
//        child2_2.setAddress("测试地址2_2");
//        child2_2.setChildName("测试姓名");
//        child2_2.setChildValue("测试值2_2");
//
//        childList2.add(child2_1);
//        childList2.add(child2_2);
//        object2.setList(childList2);
        DifferenceAttr attr = attrCompare.compareValue(object1, object2);

        attrCompare.printTrace(attr);

    }

    public void printTrace(DifferenceAttr attr) {
        System.out.println("*******打印开始********");
        for(String str:attr.getDifferenceAttrs()) {
            System.out.println(str);
        }
        if(attr.getChildrenDifference()!=null) {
            System.out.println("********打印子节点开始*********");
            for(DifferenceAttr tempAttr:attr.getChildrenDifference()) {
                printTrace(tempAttr);
            }
        }
    }

}

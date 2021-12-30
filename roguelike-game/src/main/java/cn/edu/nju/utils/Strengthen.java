package cn.edu.nju.utils;


public enum Strengthen {
    HEALTH,
    MAX_HEALTH,
    STRENGTH,
    DEFENCE;
    
    private static int index = 0;

    public static Strengthen next(){
        Strengthen[] values = Strengthen.values();
        Strengthen res = values[index];
        index++;
        index %= values.length;
        return res;
    }
    
}

package io.txcl.mingds.geom;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Region {
    private final Map<Integer, List<List<Vector2D>>> levelsToPolys;

    public Region(Map<Integer, List<List<Vector2D>>> levelsToPolys) {
        this.levelsToPolys = levelsToPolys;
    }

    public Region(){
        this(new HashMap<>());
    }

    public Region add(Region other){
        return new Region(mergeMaps(this.levelsToPolys, other.levelsToPolys));
    }

    private static Map<Integer, List<List<Vector2D>>> mergeMaps(Map<Integer, List<List<Vector2D>>> map1, Map<Integer, List<List<Vector2D>>> map2){
        Set<Integer> levels = new HashSet<>();
        levels.addAll(map1.keySet());
        levels.addAll(map2.keySet());

        Map<Integer, List<List<Vector2D>>> newMap = new HashMap<>();
        for(Integer level: levels){
            List<List<Vector2D>> polys = new ArrayList<>();

            if(map1.containsKey(level)){
                polys.addAll(map1.get(level));
            }

            if(map2.containsKey(level)){
                polys.addAll(map2.get(level));
            }

            newMap.put(level, polys);
        }

        return newMap;
    }

    public Map<Integer, List<List<Vector2D>>> getLevelsToPolys() {
        return levelsToPolys;
    }
}

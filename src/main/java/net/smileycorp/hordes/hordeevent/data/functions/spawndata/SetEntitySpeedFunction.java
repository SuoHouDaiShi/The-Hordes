package net.smileycorp.hordes.hordeevent.data.functions.spawndata;

import com.google.gson.JsonElement;
import net.smileycorp.atlas.api.data.DataType;
import net.smileycorp.hordes.common.HordesLogger;
import net.smileycorp.hordes.common.data.values.ValueGetter;
import net.smileycorp.hordes.common.event.HordeBuildSpawnDataEvent;
import net.smileycorp.hordes.hordeevent.data.functions.HordeFunction;

public class SetEntitySpeedFunction implements HordeFunction<HordeBuildSpawnDataEvent> {

    private final ValueGetter<Double> getter;

    public SetEntitySpeedFunction(ValueGetter<Double> getter) {
        this.getter = getter;
    }

    @Override
    public void apply(HordeBuildSpawnDataEvent event) {
        event.getSpawnData().setEntitySpeed(getter.get(event));
    }

    public static SetEntitySpeedFunction deserialize(JsonElement json) {
        try {
            return new SetEntitySpeedFunction(ValueGetter.readValue(DataType.DOUBLE, json));
        } catch(Exception e) {
            HordesLogger.logError("Incorrect parameters for function hordes:set_entity_speed", e);
        }
        return null;
    }
    
}

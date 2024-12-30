package tn.fst.igl5.delivery_microservice.util;

import java.util.ArrayList;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferencedWarning {

    private String key = null;
    private final ArrayList<Object> params = new ArrayList<>();

    public void addParam(final Object param) {
        params.add(param);
    }

    public String toMessage() {
        String message = key;
        if (!params.isEmpty()) {
            message += "," + params.stream().map(Object::toString).collect(Collectors.joining(","));
        }
        return message;
    }


}

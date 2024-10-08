package pl.chudziudgi.venuspvp.user;

import lombok.Getter;
import lombok.Setter;
import pl.chudziudgi.venuspvp.feature.HandItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User implements Serializable {

    private UUID uuid;
    private List<String> handItems;
    private String handItem;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.handItems = new ArrayList<>();
        this.handItem = null;
    }

    public UUID uuid() {
        return this.uuid;
    }
}


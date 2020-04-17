package models;

import org.joda.time.DateTime;

import java.util.Objects;
import java.util.UUID;

public class GeneralID implements Comparable<GeneralID> {

    UUID uuid;
    long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public GeneralID() {
        this.uuid = UUID.randomUUID();
        this.timestamp = new DateTime().getMillis();
    }

    public UUID getUUID() {
        return uuid;
    }

    //Used to compare IDs by timestamp
    @Override
    public int compareTo(GeneralID otherID) {
        return Long.compare(this.getTimestamp(), otherID.getTimestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralID generalID = (GeneralID) o;
        return Objects.equals(uuid, generalID.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}

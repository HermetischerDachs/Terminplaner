import java.util.Objects;

public class Entry {
    private String date;
    private String entry;

    public Entry(String date, String entry){
        this.date = date;
        this.entry = entry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    @Override
    public String toString(){
        return this.date + ", " + this.entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry1 = (Entry) o;
        return date.equals(entry1.date) &&
                entry.equals(entry1.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, entry);
    }
}

@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
private LocalDateTime datetime;

public LocalDateTime getDateTime() {
        return this.datetime;
}

public void setDateTime(LocalDateTime datetime) {
        this.datetime=datetime;
}

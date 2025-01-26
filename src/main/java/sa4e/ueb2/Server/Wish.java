package sa4e.ueb2.Server;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
class Wish {
    @Id
    private Long id;

    private String wishClaimer;
    private String targetAddress;
    private String wish;
    private int status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getwishClaimer() {
        return wishClaimer;
    }

    public void setwishClaimer(String wishClaimer) {
        this.wishClaimer = wishClaimer;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String address) {
        this.targetAddress = address;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

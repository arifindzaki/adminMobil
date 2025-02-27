package com.example.adminmobile.Model;

import com.google.firebase.firestore.DocumentId;

public class RiwayatBokingAdminModel {
    private String Nama, NamaMobil, Hari, Total;
    private String Tujuan;
    private String TanggalPinjam;

    public String getDocumentId() {
        return documentId;
    }

    @DocumentId
    private String documentId;

    public RiwayatBokingAdminModel() {
        this.documentId = null;
        this.Nama ="";
        this.TanggalPinjam = "";
        this.NamaMobil = "";
        this.Tujuan="";
    }
    public String getNama(){return Nama;}
    public void setNama(String Nama){this.Nama=Nama;}
    public String getNamaMobil() {
        return NamaMobil;
    }

    public void setNamaMobil(String NamaMobil) { this.NamaMobil= NamaMobil;
    }

    public String getTujuan() {return Tujuan;}

    public void setTujuan(String Tujuan) {
        this.Tujuan = Tujuan;
    }


    public String getTanggalPinjam() {
        return TanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.TanggalPinjam = tanggalPinjam;
    }

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {this.Hari = hari;}
    public String getTotal() {return Total;}

    public void setTotal(String total) {
        this.Total = total;
    }
}


package org.kathmandulivinglabs.nraprogress;

/**
 * Created by nirab on 3/20/17.
 */

public class ListItem {

    private int code;
    private String name;
    private int beneficiariesTotal;
    private int beneficiariesSurveyed;
    private int constructionComplete;
    private int constructionInProgress;
    private int constructionNotStarted;
    private int grantReceived;
    private int grantNotRecieved;
    private int installmentApplied;
    private int installmentNotApplied;

    public ListItem(int code, String name, int bft, int bfs, int cc, int cip, int cns, int gr, int gnr, int ia, int ina) {
        this.code = code;
        this.name = name;
        this.beneficiariesTotal = bft;
        this.beneficiariesSurveyed = bfs;
        this.constructionComplete = cc;
        this.constructionInProgress = cip;
        this.constructionNotStarted = cns;
        this.grantReceived = gr;
        this.grantNotRecieved = gnr;
        this.installmentApplied= ia;
        this.installmentNotApplied = ina;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeneficiariesTotal() {
        return beneficiariesTotal;
    }

    public void setBeneficiariesTotal(int beneficiariesTotal) {
        this.beneficiariesTotal = beneficiariesTotal;
    }

    public int getBeneficiariesSurveyed() {
        return beneficiariesSurveyed;
    }

    public void setBeneficiariesSurveyed(int beneficiariesSurveyed) {
        this.beneficiariesSurveyed = beneficiariesSurveyed;
    }

    public int getConstructionComplete() {
        return constructionComplete;
    }

    public void setConstructionComplete(int constructionComplete) {
        this.constructionComplete = constructionComplete;
    }

    public int getConstructionInProgress() {
        return constructionInProgress;
    }

    public void setConstructionInProgress(int constructionInProgress) {
        this.constructionInProgress = constructionInProgress;
    }

    public int getConstructionNotStarted() {
        return constructionNotStarted;
    }

    public void setConstructionNotStarted(int constructionNotStarted) {
        this.constructionNotStarted = constructionNotStarted;
    }

    public int getGrantReceived() {
        return grantReceived;
    }

    public void setGrantReceived(int grantReceived) {
        this.grantReceived = grantReceived;
    }

    public int getGrantNotRecieved() {
        return grantNotRecieved;
    }

    public void setGrantNotRecieved(int grantNotRecieved) {
        this.grantNotRecieved = grantNotRecieved;
    }

    public int getInstallmentApplied() {
        return installmentApplied;
    }

    public void setInstallmentApplied(int installmentApplied) {
        this.installmentApplied = installmentApplied;
    }

    public int getInstallmentNotApplied() {
        return installmentNotApplied;
    }

    public void setInstallmentNotApplied(int installmentNotApplied) {
        this.installmentNotApplied = installmentNotApplied;
    }
}

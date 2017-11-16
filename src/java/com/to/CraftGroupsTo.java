/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

/**
 *
 * @author pranesh
 */
public class CraftGroupsTo {

    private String craftGroupId;
    private String craftGroupDescription;

    public CraftGroupsTo() {
    }

    public CraftGroupsTo(String craftGroupId, String craftGroupDescription) {
        this.craftGroupId = craftGroupId;
        this.craftGroupDescription = craftGroupDescription;
    }

    public String getCraftGroupId() {
        return craftGroupId;
    }

    public void setCraftGroupId(String craftGroupId) {
        this.craftGroupId = craftGroupId;
    }

    public String getCraftGroupDescription() {
        return craftGroupDescription;
    }

    public void setCraftGroupDescription(String craftGroupDescription) {
        this.craftGroupDescription = craftGroupDescription;
    }

}

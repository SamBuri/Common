/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.dbaccess.CountyDA;
import com.saburi.common.dbaccess.LookupDataDA;
import com.saburi.common.dbaccess.LookupObjectDA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Hp
 */
public class CommonDefaultData {

    protected List<LookupObjectDA> lookupObjectDAs = new ArrayList<>(
            Arrays.asList(
                    new LookupObjectDA(1, "Country", false, false),
                    new LookupObjectDA(2, "Marital Status", false, false),
                    new LookupObjectDA(3, "Staff Title", false, false),
                    new LookupObjectDA(4, "Ocupation", false, false),
                    new LookupObjectDA(5, "District", false, false),
                    new LookupObjectDA(6, "Religion", false, false),
                    new LookupObjectDA(7, "Qualification", false, false),
                    new LookupObjectDA(8, "Department", false, false)
            )
    );

    //                    LookupData
    protected List<LookupDataDA> lookupDataDAs = new ArrayList<>(
            Arrays.asList(
                    new LookupDataDA(1, "Uganda", false, false, true),
                    new LookupDataDA(2, "Married", false, false, true),
                    new LookupDataDA(2, "Single", false, false, true),
                    new LookupDataDA(5, "Kampala", false, false, true)
            )
    );
    

    protected void save() {

        lookupObjectDAs.stream().forEach(e -> {
            try {
                e.save();
            } catch (Exception ex) {

            }
        });

        lookupDataDAs.stream().forEach(e -> {
            try {
                e.save();
            } catch (Exception ex) {

            }
        });

    }
}

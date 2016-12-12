package com.dazz.nubiasettings.modem;

import java.util.HashMap;

public class ModemConfig {

	public static final String CMCC = "OTA_/system/etc/modem_ota/CM/mcfg_sw.mbn1351663707:295912";
	public static final String CU = "OTA_/system/etc/modem_ota/CU/mcfg_sw.mbn1351663707:603591";
	public static final String CT = "OTA_/system/etc/modem_ota/CT/mcfg_sw.mbn1351663707:739709";

	public static final String CM_CONFIG_NAME = "Volte_OpenMkt-Commercial-CMCC";
	public static final String CU_CONFIG_NAME = "OpenMkt-Commercial-CU";
	public static final String CT_CONFIG_NAME = "OpenMkt-Commercial-CT";
	
	public static final HashMap<String, String> mOperatorMBN = new HashMap<String,String>();
	
	public static final HashMap<String, String> mConfigName = new HashMap<String,String>();
	static{
		mOperatorMBN.put(CM_CONFIG_NAME, CMCC);
		mOperatorMBN.put(CU_CONFIG_NAME, CU);
		mOperatorMBN.put(CT_CONFIG_NAME, CT);
		
		mConfigName.put("CM", CM_CONFIG_NAME);
		mConfigName.put("CU", CU_CONFIG_NAME);
		mConfigName.put("CT", CT_CONFIG_NAME);
	}
}

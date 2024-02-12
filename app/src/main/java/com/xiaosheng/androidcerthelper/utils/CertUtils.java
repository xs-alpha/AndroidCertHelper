package com.xiaosheng.androidcerthelper.utils;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.xiaosheng.androidcerthelper.entiy.CertInfo;

import java.io.File;
import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertUtils {

    public static List<CertInfo> loadCerts(File[] files){
        ArrayList<CertInfo> certInfos = new ArrayList<>();
        if (files != null) {
            for (File certFile : files) {
                if (certFile.isFile() && certFile.getName().endsWith(".0")) {
                    try {
                        FileInputStream fis = new FileInputStream(certFile);
                        CertificateFactory cf = CertificateFactory.getInstance("X.509");
                        Certificate cert = cf.generateCertificate(fis);
                        fis.close();

                        if (cert instanceof X509Certificate) {
                            X509Certificate x509Cert = (X509Certificate) cert;
                            Date notAfter = x509Cert.getNotAfter();

                            if (notAfter.before(new Date())) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String expiryDate = dateFormat.format(notAfter);

                                LogUtils.log("文件名: " + certFile.getName());
                                LogUtils.log("过期时间:"+expiryDate);
//                                System.out.println("证书信息: " + x509Cert.toString());
                                String issuer = x509Cert.getIssuerDN().getName();
                                String subject = x509Cert.getSubjectDN().getName();

                                LogUtils.log("颁发者:"+issuer);
                                LogUtils.log("主题:"+subject);
                                LogUtils.log("-------------------------------------------");

                                CertInfo certInfo = new CertInfo();
                                certInfo.setExpireTm(expiryDate);
                                certInfo.setIssuer(issuer);
                                certInfo.setTitle(certFile.getName());

                                certInfos.add(certInfo);
                            }
                        }
                    } catch (Exception e) {
                        LogUtils.loge(e.getMessage());
                        ToastUtils.showLong(e.getMessage());
                    }
                }
            }
        }
        return certInfos;
    }
}

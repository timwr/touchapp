package com.headsup.remote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.View;

public class SplashActivity extends ActionBarActivity {

	private static final long SPLASH_SCREEN_DELAY = 3000;
	private static boolean sApplicationLoaded = false;

    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        View view = findViewById(R.id.layout_splash);
        sharedPreferences = getSharedPreferences("remote", MODE_PRIVATE);

		if (sApplicationLoaded) {
			launchApplication();
		} else {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchApplication();
                }
            }, SPLASH_SCREEN_DELAY);
        }
	}

    private boolean hasAccepted() {
        boolean accepted = sharedPreferences.getBoolean("accepted", false);
        return accepted;
    }

    private void setAccepted() {
        sharedPreferences.edit().putBoolean("accepted", true).commit();
    }

    private void launchApp() {
        sApplicationLoaded = true;
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

	private void launchApplication() {
		if (!isFinishing()) {
            if (hasAccepted()) {
                launchApp();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Dialog));
            builder.setMessage("BETA APP LICENSE AGREEMENT\n" +
                    "HEADS UP SOFTWARE INTERNATIONAL, INC. (\"HEADS UP\") LICENSES THIS SOFTWARE PRODUCT TO YOU SUBJECT TO THE TERMS CONTAINED IN THIS END USER LICENSE AGREEMENT (THIS \"AGREEMENT\" or \"EULA\"). READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY BEFORE INSTALLING, COPYING AND USING THIS COMPUTER SOFTWARE AND THE ACCOMPANYING DOCUMENTATION (THE \"SOFTWARE\"). THE SOFTWARE IS COPYRIGHTED AND IT IS LICENSED TO YOU UNDER THIS EULA, NOT SOLD TO YOU. BY INSTALLING, COPYING OR OTHERWISE USING THE SOFTWARE, YOU AGREE TO BE BOUND BY THE TERMS OF THIS EULA. IF YOU ARE NOT WILLING TO BE BOUND BY THE TERMS OF THIS EULA, DO NOT INSTALL, COPY OR USE THE SOFTWARE AND YOU SHOULD RETURN THE PACKAGE TO THE PLACE WHERE YOU GET IT WITHIN THIRTY (30) DAYS.\n" +
                    "THIS EULA IS A LEGAL AGREEMENT CONCERNING THE SOFTWARE BETWEEN YOU, AS EITHER AN INDIVIDUAL OR A SINGLE BUSINESS ENTITY AND HEADS UP. THIS AGREEMENT SUPERSEDES AND REPLACES ANY PRIOR PROPOSAL, REPRESENTATION, OR UNDERSTANDING YOU MAY HAVE HAD WITH HEADS UP RELATING TO THE SOFTWARE.\n" +
                    "Assent to Be Bound\n" +
                    "By touching the \"I accept the terms...\" button on the beta tester page, by executing a written copy of this Agreement, or by installing, copying or otherwise using this Software, you agree to be bound by the terms of this Agreement. If you do not agree with any term or condition, do not download, order, open, install or use the Software or product package. Contact Heads Up to arrange the return of the Software and accompanying materials to Heads Up at no charge to you.\n" +
                    "BETA DISCLAIMER\n" +
                    "THE BETA SOFTWARE LICENSED HEREUNDER IS BELIEVED TO CONTAIN DEFECTS AND A PRIMARY PURPOSE OF THIS BETA TESTING LICENSE IS TO OBTAIN FEEDBACK ON SOFTWARE PERFORMANCE AND THE IDENTIFICATION OF DEFECTS. LICENSEE IS ADVISED TO SAFEGUARD IMPORTANT DATA, TO USE CAUTION AND NOT TO RELY IN ANY WAY ON THE CORRECT FUNCTIONING OR PERFORMANCE OF THE SOFTWARE AND/OR ACCOMPANYING MATERIALS.\n" +
                    "Confidentiality\n" +
                    "You agree that, unless otherwise specifically provided herein or agreed by the Heads Up in writing, the Software and the Documentation, including the specific design and structure of individual programs and the Software, provided to you by Heads Up constitute confidential proprietary information of Heads Up. You shall permit only authorized users, who possess rightfully, obtained license keys, to use the Software or to view the Documentation. You agree not to transfer, copy, disclose, provide or otherwise make available such confidential information in any form to any third party without the prior written consent of Heads Up. You agree to implement reasonable security measures to protect such confidential information, but without limitation to the foregoing, shall use best efforts to maintain the security of the Software provided to you by Heads Up. You will use your best efforts to cooperate with and assist Heads Up in identifying and preventing any unauthorized use, copying, or disclosure of the Software, Documentation, or any portion thereof.\n" +
                    "Feedback\n" +
                    "It is expressly understood, acknowledged and agreed that you shall, regardless of whether or not formally requested to do, provide to Heads Up reasonable suggestions, comments and feedback regarding the Software, including but not limited to usability, bug reports and test results, with respect to Software testing (collectively, \"Feedback\"). If you provide such Feedback to Heads Up, you shall grant Heads Up the following worldwide, non-exclusive, perpetual, irrevocable, royalty free, fully paid up rights: (i) to make, use, copy, modify, sell, distribute, sub-license, and create derivative works of, the Feedback as part of any Heads Up product, technology, service, specification or other documentation (individually and collectively, \"Heads Up Products\"); (ii) to publicly perform or display, import, broadcast, transmit, distribute, license, offer to sell, and sell, rent, lease or lend copies of the Feedback (and derivative works thereof) as part of any Heads Up Product; (iii) solely with respect to Licensee's copyright and trade secret rights, to sublicense to third parties the foregoing rights, including the right to sublicense to further third parties; and (iv) to sublicense to third parties any claims of any patents owned or licensable by Licensee that are necessarily infringed by a third party product, technology or service that uses, interfaces, interoperates or communicates with the feedback or portion thereof incorporated into a Heads Up Product, technology or service. Further, you warrant that your Feedback is not subject to any license terms that would purport to require Heads Up to comply with any additional obligations with respect to any Heads Up Products that incorporate any Feedback.\n" +
                    "Grant of License\n" +
                    "Subject to the terms and conditions of this Agreement, Heads Up hereby grants to you a non-exclusive, non-transferable license (without the right to sublicense) (i) to use the Software in accordance with the Documentation solely for purposes of internal testing and evaluation, and (ii) to copy Software for archival or backup purposes, provided that all titles and trademarks, copyright, and restricted rights notices are reproduced on such copies.\n" +
                    "Restrictions on Grant\n" +
                    "Except as otherwise specifically permitted in this Agreement, you may not: (a) modify or create any derivative works of any Software or documentation, including translation or localization; (code written to published APIs (application programming interfaces) for the Software shall not be deemed derivative works); (b) copy the Software except as provided in this Agreement or elsewhere by Heads Up; (c) separate Software, which is licensed as a single product, into its component parts. (d) sublicense or permit simultaneous use of the Software by more than one user; (e) reverse engineer, decompile, or disassemble or otherwise attempt to derive the source code for any Product the Software (except to the extent applicable laws specifically prohibit such restriction); (f) redistribute, encumber, sell, rent, lease, sublicense, use the Software in a timesharing or service bureau arrangement, or otherwise transfer rights to any Software. You may NOT transfer the Software under any circumstances; (g) remove or alter any trademark, logo, copyright or other proprietary notices, legends, symbols or labels in the Product(s); (h) publish any results of benchmark tests run on any Software to a third party without Heads Up prior written consent; or (i) use any Software on a system with more CPUs than the number licensed, by more users than have been licensed, on more computers or computing devices than the number licensed, or by more developers than the number licensed, as applicable.\n" +
                    "Beta-Software Product Support\n" +
                    "Heads Up is under no obligation to provide technical support under the terms of this license, and provides no assurance that any specific errors or discrepancies in the Software will be corrected.\n" +
                    "Ownership and Copyright of Software\n" +
                    "Title to the Software and all copies thereof remain with Heads Up and/or or its suppliers. The Software is copyrighted and is protected by United States copyright laws and international treaty provisions. Licensee will not remove copyright notices from the Software. Licensee agrees to prevent any unauthorized copying of the Software. Except as expressly provided herein, Heads Up does not grant any express or implied right to you under Heads Up patents, copyrights, trademarks, or trade secret information.\n" +
                    "Term Of This Agreement\n" +
                    "Your rights with respect to the Beta Software will terminate upon the earlier of (a) the initial commercial release by Heads Up of a generally available version of the Software or (b) six months after the last date you receive the Software or any update thereto. Either party may terminate this Agreement at any time for any reason or no reason by providing the other party advance written notice thereof. Upon any expiration or termination of this Agreement, the rights and licenses granted to you under this Agreement shall immediately terminate, and you shall immediately cease using, and will return to Heads Up (or, at Heads Up' request, destroy), the Software, Documentation, and all other tangible items in your possession or control that are proprietary to or contain Confidential Information.\n" +
                    "Disclaimer\n" +
                    "THE SOFTWARE AND DOCUMENTATION ARE LICENSED \"AS IS\", AND HEADS UP DISCLAIMS ANY AND ALL OTHER WARRANTIES, WHETHER EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE, TO THE EXTENT AUTHORIZED BY LAW. WITHOUT LIMITATION OF THE FOREGOING, HEADS UP EXPRESSLY DOES NOT WARRANTS THAT THE SOFTWARE WILL MEET YOUR REQUIREMENTS OR THAT OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED OR ERROR FREE. YOU ASSUME RESPONSIBILITY FOR SELECTING THE SOFTWARE TO ACHIEVE YOUR INTENDED RESULTS, AND FOR THE RESULTS OBTAINED FROM YOUR USE OF THE SOFTWARE. YOU SHALL BEAR THE ENTIRE RISK AS TO THE QUALITY AND THE PERFORMANCE OF THE SOFTWARE.\n" +
                    "Limitation of Liability\n" +
                    "Provision of any Software under this Agreement is experimental and shall not create any obligation for Heads Up to continue to develop, productize, support, repair, offer for sale or in any other way continue to provide or develop Software either to Licensee or to any other party.\n" +
                    "HEADS UP CUMULATIVE LIABILITY TO YOU OR ANY PARTY RELATED TO YOU FOR ANY LOSS OR DAMAGES ARISING OUT OF OR RELATING TO THIS AGREEMENT, OR INSTALLATION OR USE OF THE SOFTWARE AND DOCUMENTATION SHALL NOT EXCEED THE AMOUNT OF LICENSE FEES PAID TO HEADS UP BY YOU UNDER THIS AGREEMENT. THIS LIMITATION APPLIES TO ALL CAUSES OF ACTION OR CLAIMS IN THE AGGREGATE, INCLUDING, WITHOUT LIMITATION, BREACH OF CONTRACT, BREACH OF WARRANTY, INDEMNITY, NEGLIGENCE, STRICT LIABILITY, MISREPRESENTATION, AND OTHER TORTS. IN NO EVENT SHALL HEADS UP BE LIABLE TO YOU OR ANY PARTY RELATED TO YOU FOR ANY INDIRECT, INCIDENTAL, CONSEQUENTIAL, SPECIAL, EXEMPLARY, OR PUNITIVE DAMAGES OR LOST PROFITS, EVEN IF HEADS UP HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.\n" +
                    "Governing Law and Choice of Forum\n" +
                    "This Agreement shall be governed by and interpreted in accordance with the laws of the United Kingdom of Great Britain and Northern Ireland, without regard to the conflicts of law rules thereof. Any claim or dispute arising in connection with this EULA shall be resolved in the courts situated within the United Kingdom. To the maximum extent permitted by law, you hereby consent to the jurisdiction and venue of such courts and waive any objections to the jurisdiction or venue of such courts.\n" +
                    "Export Restrictions\n" +
                    "You acknowledge that Software is of U. K. origin. Recipient agrees to comply with all applicable international and national laws that apply to the Software.\n" +
                    "Entire Agreement\n" +
                    "This Agreement constitutes the complete and exclusive agreement between you and Heads Up with respect to the subject matter hereof, and supersedes all prior or contemporaneous oral or written communications, proposals, representations, understandings, or agreements not specifically incorporated herein. This Agreement may not be amended except in a writing duly signed by you and an authorized representative of Heads Up.\n" +
                    "Contact Info\n" +
                    "Copyright (C) 2013-2014 Heads Up Ventures. All rights reserved. \n" +
                    "Product Site: www.headsupventures.com \n" +
                    "If you have any questions please contact us via http://headsupventures.com/contact/ \n");
            builder.setTitle("Terms and conditions");
            builder.setPositiveButton("I accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setAccepted();
                    launchApp();
                }
            });
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
		}
	}
}
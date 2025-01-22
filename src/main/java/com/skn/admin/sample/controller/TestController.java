package com.skn.admin.sample.controller;

import java.util.List;

import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.util.NTCryptoUtil;
import com.skn.admin.util.NTUtil;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skn.admin.sample.dto.DecodeValue;
import com.skn.admin.sample.service.TestService;
import com.skn.admin.sample.util.KISA_SEED_CBC;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

	private final TestService testService;
	private static final Logger logger = LoggerFactory.getLogger(Example.class);
	
	@GetMapping("/mig-encrypt")
	@ResponseBody
	public String encrypt() {

		List<DecodeValue> inqList = testService.getList();
		if (inqList.size() > 0)
			for(DecodeValue decode : inqList) {

				if(StringUtils.hasText(decode.getInqNam())) {
					byte[] byNam = decode.getInqNam().getBytes();
					decode.setInqNam(KISA_SEED_CBC.byteArrayToHexString(KISA_SEED_CBC.SEED_CBC_Encrypt(byNam, 0, byNam.length)));
				} else {
					decode.setInqNam("");
				}

				if(StringUtils.hasText(decode.getInqEma())) {
					byte[] byEma = decode.getInqEma().getBytes();
					decode.setInqEma(KISA_SEED_CBC.byteArrayToHexString(KISA_SEED_CBC.SEED_CBC_Encrypt(byEma, 0, byEma.length)));
				} else {
					decode.setInqEma("");
				}

				if(StringUtils.hasText(decode.getInqTel())) {
					byte[] byTel = decode.getInqTel().getBytes();
					decode.setInqTel(KISA_SEED_CBC.byteArrayToHexString(KISA_SEED_CBC.SEED_CBC_Encrypt(byTel, 0, byTel.length)));
				} else {
					decode.setInqTel("");
				}

				decode.setInqCon(decode.getInqCon().replaceAll("\n", "<br/>"));

				testService.insert(decode);
			}


		return inqList.size() + "건 마이그레이션 완료";
	}

	/**
	 * getBytes
	 * SEED_CBC_Encrypt
	 * byteArrayToHexString
	 *
	 * hexStringToByteArray
	 * SEED_CBC_Decrypt
	 * new String
	 *
	 * @return
	 */
	@GetMapping("/mig-decrypt")
	@ResponseBody
	public String decrypt() {
		List<DecodeValue> inqList = testService.getList();
		if (inqList.size() > 0)
			for(DecodeValue decode : inqList) {

				if(StringUtils.hasText(decode.getInqNam())) {
					byte[] byNam = KISA_SEED_CBC.hexStringToByteArray(decode.getInqNam());
					try {
						decode.setInqNam(new String(KISA_SEED_CBC.SEED_CBC_Decrypt(byNam, 0, byNam.length), "euc-kr"));
					} catch(Exception e) {

						decode.setInqNam("ERROR");
					}
				} else {
					decode.setInqNam("");
				}

				if(StringUtils.hasText(decode.getInqEma())) {
					byte[] byEma = KISA_SEED_CBC.hexStringToByteArray(decode.getInqEma());
					try {
						decode.setInqEma(new String(KISA_SEED_CBC.SEED_CBC_Decrypt(byEma, 0, byEma.length), "euc-kr"));
					} catch(Exception e) {

						decode.setInqEma("ERROR");
					}

				} else {
					decode.setInqEma("");
				}

				if(StringUtils.hasText(decode.getInqTel())) {
					byte[] byTel = KISA_SEED_CBC.hexStringToByteArray(decode.getInqTel());
					decode.setInqTel(new String(KISA_SEED_CBC.SEED_CBC_Decrypt(byTel, 0, byTel.length)));
				} else {
					decode.setInqTel("");
				}

				 // decode.setInqCon(decode.getInqCon().replaceAll("\n", "<br/>"));

				testService.insert(decode);
			}


		return inqList.size() + "건 마이그레이션 완료";

	}

	@GetMapping("/mig-insert")
	@ResponseBody
	public String insert() {
		List<DecodeValue> inqList = testService.selectInquiryOld();
		if (inqList.size() > 0)
			for(DecodeValue decode : inqList) {

				Inquiry data = new Inquiry();
				data.setUid(RandomStringUtils.randomAlphanumeric(16));
				data.setInquiryIndex(decode.getInqIdx());
				data.setLang(decode.getInqCouCod());
				data.setTitle(decode.getInqTit());
				data.setContent(decode.getInqCon());
				data.setEmail(NTCryptoUtil.encrypt(decode.getInqEma(), "EMAIL"));
				data.setName(decode.getInqNam());
				String etc1 = NTUtil.isNull(decode.getInqOffNam(), "");
				String etc = etc1;
				String newEtc = "";
				if(!"".equals(etc1))
					etc += " "+NTUtil.isNull(decode.getInqTel(), "");

				newEtc = etc;
//				try {
//					byte[] b = etc.getBytes("euc-kr");
//					newEtc = new String(b);
//				} catch(Exception e) {
//					logger.error("!!!!!!!!!!!!!!!!!!!!!!!ERROR!!!!!!!!!"+e.getMessage());
//					newEtc = etc;
//				}



				data.setEtcInfo(NTCryptoUtil.encrypt(newEtc, "ETC"));

				data.setClassification(decode.getInqTyp());
				data.setStatus(decode.getInqIp());
				data.setModDate(decode.getInqUpd());
				data.setRegDate(decode.getInqReg());

				int ar = testService.insertInquiry(data);


			}


		return inqList.size() + "건 마이그레이션 완료";

	}
	
}

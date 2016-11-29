/*   
 * Copyright (c) 2015-2020 FuJianZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */package lyu.klt.graduationdesign.moudle.client;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * 二维码相关操作
 * 
 * @Author 康良涛
 * @Version 1.0
 * @CreateTime 2016-8-1 下午3:26:05
 */
public class ZXingImage {
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xffffffff;
	private static final int PADDING_SIZE_MIN = 20; // 最小留白长度, 单位: px

	/**
	 * 
	 * @Title: createQRCode
	 * @Description: TODO(生成二维码图片)
	 * @CreateBy: 康良涛
	 * @CreateTime: 2016-8-2 上午8:52:31
	 * @UpdateBy: 康良涛
	 * @UpdateTime: 2016-8-2 上午8:52:31
	 * @param str
	 * @param widthAndHeight
	 * @return
	 * @throws WriterException
	 *             Bitmap
	 * @throws
	 */
	public static Bitmap createQRCode(Context context, String str,
			int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints);

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];

		boolean isFirstBlackPoint = false;
		int startX = 0;
		int startY = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					if (isFirstBlackPoint == false) {
						isFirstBlackPoint = true;
						startX = x;
						startY = y;

					}

					pixels[y * width + x] = BLACK;
				} else {
					pixels[y * width + x] = WHITE;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		// 剪切中间的二维码区域，减少padding区域
		if (startX <= PADDING_SIZE_MIN)
			return bitmap;

		int x1 = startX - PADDING_SIZE_MIN;
		int y1 = startY - PADDING_SIZE_MIN;
		if (x1 < 0 || y1 < 0)
			return bitmap;

		int w1 = width - x1 * 2;
		int h1 = height - y1 * 2;

		Bitmap bitmapQR = Bitmap.createBitmap(bitmap, x1, y1, w1, h1);
		Bitmap logoBitmap = BitmapFactory.decodeResource(
				context.getResources(), R.drawable.zhzj);
		bitmapQR=addLogo(bitmapQR, logoBitmap);
		return bitmapQR;
	}

	/**
	 * 
	 * @Title: addLogo
	 * @Description: TODO(往二维码中添加logo)
	 * @CreateBy: 康良涛
	 * @CreateTime: 2016-8-2 上午8:52:48
	 * @UpdateBy: 康良涛
	 * @UpdateTime: 2016-8-2 上午8:52:48
	 * @param src
	 * @param logo
	 * @return Bitmap
	 * @throws
	 */
	private static Bitmap addLogo(Bitmap src, Bitmap logo) {
		if (src == null) {
			return null;
		}

		if (logo == null) {
			return src;
		}

		// 获取图片的宽高
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		if (srcWidth == 0 || srcHeight == 0) {
			return null;
		}

		if (logoWidth == 0 || logoHeight == 0) {
			return src;
		}

		// logo大小为二维码整体大小的1/5
		float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
		Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight,
				Bitmap.Config.ARGB_8888);
		try {
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(src, 0, 0, null);
			canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
			canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2,
					(srcHeight - logoHeight) / 2, null);

			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}

		return bitmap;
	}

}

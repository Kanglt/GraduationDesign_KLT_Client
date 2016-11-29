package lyu.klt.frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import lyu.klt.frame.ab.util.AbLogUtil;

/**
 * 
 * @author ZhenGui Dai
 * @since 2013-2-1
 */
public abstract class BitmapUtil {

	private static final String TAG = BitmapUtil.class.getSimpleName()
			.toString();
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * .
	 * 
	 * @param containerWidth
	 *            The imageView/container width
	 * @param originalWidth
	 *            The image width
	 * @param originalHeight
	 *            The image height
	 * @return A point that has x, y values to represent width & height
	 */
	public static Point getScaledSize(int containerWidth, int originalWidth,
			int originalHeight) {

		// based on the container width, calculated the scaled height.
		// note: this function assumes containerWidth is static, and
		// containerHeight is currently not implemented yet.

		if (originalWidth == 0) {
			throw new ArithmeticException("Divide by 0: originalHeight");
		}

		int scaled_height = 0;
		Point point = new Point();

		scaled_height = (containerWidth * originalHeight) / originalWidth;

		// if(originalWidth > originalHeight){
		// scaled_height = (containerWidth * originalWidth) / originalHeight;
		//
		// //videoModel.setImgWidth(img_width);
		// }else if(originalHeight > originalWidth){
		// scaled_height = (containerWidth * originalWidth) / originalHeight;
		// }
		// else{
		// // equal size
		// scaled_height = containerWidth;
		// }

		point.x = containerWidth;
		point.y = scaled_height;
		return point;
	}

	/**
	 * .
	 * 
	 * @param containerWidth
	 *            The imageView/container width
	 * @param originalWidth
	 *            The image width
	 * @param originalHeight
	 *            The image height
	 * @return A point that has x, y values to represent width & height
	 */
	public static Point getScaledHeightSize(int containerHeight,
			int originalWidth, int originalHeight) {
		if (originalHeight == 0) {
			throw new ArithmeticException("Divide by 0: originalHeight");
		}
		int scaled_width = 0;
		Point point = new Point();
		scaled_width = (containerHeight * originalWidth) / originalHeight;
		point.x = scaled_width;
		point.y = containerHeight;
		return point;
	}

	/**
	 * Decode and sample down a bitmap from resources to the requested width and
	 * height.
	 * 
	 * @param res
	 *            The resources object containing the image data
	 * @param resId
	 *            The resource id of the image data
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * Decode and sample down a bitmap from a file to the requested width and
	 * height.
	 * 
	 * @param filename
	 *            The full path of the file to decode
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static synchronized Bitmap decodeSampledBitmapFromFile(
			String filename, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return BitmapFactory.decodeFile(filename, options);
	}

	/**
	 * Decode and sample down a bitmap from a file to the requested width and
	 * height.
	 * 
	 * @param filename
	 *            The full path of the file to decode
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static synchronized Bitmap decodeRecordBitmapFromFile(
			String filename, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return getBitmapThumbnails(BitmapFactory.decodeFile(filename, options),
				reqWidth, reqHeight);
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
	 * object when decoding bitmaps using the decode* methods from
	 * {@link BitmapFactory}. This implementation calculates the closest
	 * inSampleSize that will result in the final decoded bitmap having a width
	 * and height equal to or larger than the requested width and height. This
	 * implementation does not ensure a power of 2 is returned for inSampleSize
	 * which can be faster when decoding but results in a larger bitmap which
	 * isn't as useful for caching purposes.
	 * 
	 * @param options
	 *            An options object with out* params already populated (run
	 *            through a decode* method with inJustDecodeBounds==true
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further.
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	public static Bitmap getBitmapThumbnails(Bitmap bitmap, int reqWidth,
			int reqHeight) {

		int ivWidth = reqWidth;
		int ivHeight = reqHeight;
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();

		// 瀹藉拰楂樼殑姣斾�?
		float scaleWidth = (float) ivWidth / bmpWidth;
		float scaleHeight = (float) ivHeight / bmpHeight;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, true);
		/*
		 * if(bitmap.getWidth() >= bitmap.getHeight()) return
		 * Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true); else
		 * return bitmap;
		 */
		return resizeBmp;

	}

	/**
	 * @param filePath
	 *            The picture save sdcard path.
	 * @return The bitmap object.
	 */
	public static Bitmap getBitmapFile(final String filePath) {

		Bitmap btp = null;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(filePath);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			btp = BitmapFactory.decodeStream(inputStream, null, options);
			inputStream.close();
			int angle = getPictureRotation(filePath);
			if (angle != 0)
				return rotateBitmap(btp, angle);
			else
				return btp;
		} catch (FileNotFoundException e) {
			Log.e(TAG, "FileNotFoundException:" + e.toString());
		} catch (IOException e) {
			Log.e(TAG, "IOException:" + e.toString());
		}
		return null;
	}

	/**
	 * @Title: getPictureRotation
	 * @Description: TODO(获取图片的方向)
	 * @param: @param picturePath
	 * @param: @return
	 * @return: int
	 * @throws
	 */
	public static int getPictureRotation(String picturePath) {
		int angle = 0;
		try {
			ExifInterface exif;
			exif = new ExifInterface(picturePath);
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				angle = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				angle = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				angle = 270;
				break;
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return angle;
	}

	/**
	 * This method action is get the bitmap from Uri.
	 * 
	 * @param mUri
	 *            The picture save in the system Uri.
	 * @return The bitmap object.
	 */
	// public static Bitmap getBitmapUri(String mUri) {
	// long id = ContentUris.parseId(Uri.parse(mUri));
	// ContentResolver mContentResolver = StampApplication.getInstance()
	// .getContentResolver();
	// return Video.Thumbnails.getThumbnail(mContentResolver, id,
	// Video.Thumbnails.MINI_KIND, null);
	// }

	/**
	 * @param url
	 *            Image url
	 * @param filePath
	 *            The picture save sdcard path.
	 * @return The picture bitmap object.
	 */
	public static Bitmap getBitmapUrl(final String url, final String filePath) {
		File file = new File(filePath);
		HttpClient hc = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse rp = null;
		try {
			rp = hc.execute(get);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "ClientProtocolException:" + e.toString());
		} catch (IOException e) {
			Log.e(TAG, "IOException:" + e.toString());
		}

		if ((rp != null)
				&& rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is;
			FileOutputStream output = null;
			try {
				is = rp.getEntity().getContent();
				output = new FileOutputStream(file);
				byte[] buffer = new byte[512];
				int i;
				while ((i = is.read(buffer, 0, buffer.length)) != -1) {
					Log.d(TAG, "" + i);
					output.write(buffer, 0, i);
				}
			} catch (IllegalStateException e) {
				Log.e(TAG, "IllegalStateException:" + e.toString());
			} catch (IOException e) {
				Log.e(TAG, "IOException:" + e.toString());
			} finally {
				if (output != null) {
					try {
						output.flush();
						output.close();
					} catch (final IOException e) {
						Log.e(TAG, e.toString());
					}
				}
			}
		}
		Log.d(TAG, "success");

		return getBitmapFile(filePath);
	}

	/**
	 * this method is write an bitmap into sdcard
	 * 
	 * @param bitmap
	 */
	public static void writeBitmap(final Bitmap bitmap, final String filePath) {
		Log.d("abcd", filePath);
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileOutputStream ous = null;
				try {
					ous = new FileOutputStream(filePath);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ous);
				} catch (FileNotFoundException e) {
					Log.e(TAG, e.toString());
				} finally {
					if (ous != null) {
						try {
							ous.flush();
							ous.close();
						} catch (final IOException e) {
							Log.e(TAG, e.toString());
						}
					}
				}
			}
		}).start();
	}

	/**
	 * this method is write an smail bitmap into sdcard
	 * 
	 * @param bitmap
	 */
	public static void writeSmialBitmap(final Bitmap bitmap,
			final String filePath) {

		FileOutputStream ous = null;
		try {
			ous = new FileOutputStream(filePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 85, ous);
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.toString());
		} finally {
			if (ous != null) {
				try {
					ous.flush();
					ous.close();
				} catch (final IOException e) {
					Log.e(TAG, e.toString());
				}
			}
		}
	}

	/**
	 * This method action is get the bitmap from the ByteArray.
	 * 
	 * @param data
	 * @return bitmap
	 */
	public static Bitmap decodeByteArray(byte[] data) {
		Bitmap bm1 = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		bm1 = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		return bm1;
	}

	/**
	 * This method action is to decode bitmap form byte[] with reqWidth and
	 * reqHeight size
	 * 
	 * @param data
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data, int reqWidth,
			int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return getBitmapThumbnails(
				BitmapFactory.decodeByteArray(data, 0, data.length, options),
				reqWidth, reqHeight);
	}

	/**
	 * This method action is to rotate the bitmap.
	 * 
	 * @param bitmap
	 *            rotate bitmap object
	 * @param angle
	 *            rotate angle
	 * @return rotated bitmap object
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * This method action is to get an Rounded Corner Bitmap from an bitmap
	 * 
	 * @param paramBitmap
	 * @param paramInt
	 *            angle
	 * @return Rounded Corner Bitmap
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, int paramInt) {
		Bitmap localBitmap;
		if (paramBitmap == null) {
			localBitmap = null;
			return localBitmap;
		}
		localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(),
				paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		Rect localRect = new Rect(0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight());
		RectF localRectF = new RectF(localRect);
		float f = paramInt;
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawRoundRect(localRectF, f, f, localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
		paramBitmap.recycle();
		return localBitmap;
	}

	/**
	 * This method action is to get an CirCle Bitmap from an bitmap
	 * 
	 * @param paramBitmap
	 * @return CirCle Bitmap
	 */
	public static Bitmap getCirCleBitmap(Bitmap paramBitmap) {
		Bitmap localBitmap;
		if (paramBitmap == null) {
			localBitmap = null;
			return localBitmap;
		}
		localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(),
				paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		Rect localRect = new Rect(0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight());
		localPaint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localPaint.setColor(-12434878);
		localCanvas.drawCircle(paramBitmap.getWidth() / 2,
				paramBitmap.getWidth() / 2, paramBitmap.getWidth() / 2,
				localPaint);
		localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
		paramBitmap.recycle();
		return localBitmap;
	}

	public static Bitmap getSmallBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int w = width;
		int h = height;
		if (width > height) {
			w = 172;
			h = (int) (Math.round(((float) (height * w) / width)));
		} else {
			h = 142;
			w = (int) (Math.round(((float) (width * h) / height)));

		}
		Bitmap bit = ThumbnailUtils.extractThumbnail(bitmap, w, h);
		return bit;
	}

	private static Bitmap grabPixels(int x, int y, int w, int h) {
		int b[] = new int[w * (y + h)];
		int bt[] = new int[w * h];
		IntBuffer ib = IntBuffer.wrap(b);
		ib.position(0);
		GLES20.glReadPixels(x, 0, w, y + h, GLES20.GL_RGBA,
				GLES20.GL_UNSIGNED_BYTE, ib);
		for (int i = 0, k = 0; i < h; i++, k++) {
			for (int j = 0; j < w; j++) {
				int pix = b[i * w + j];
				int pb = (pix >> 16) & 0xff;
				int pr = (pix << 16) & 0x00ff0000;
				int pix1 = (pix & 0xff00ff00) | pr | pb;
				bt[(h - k - 1) * w + j] = pix1;
			}
		}
		Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
		return sb;
	}

	/**
	 * create the bitmap from a byte array
	 * 
	 * @param src
	 *            the bitmap object you want proecss
	 * @param watermark
	 *            the water mark above the src
	 * @return return a bitmap object ,if paramter's length is 0,return null
	 */
	private Bitmap createBitmap(Bitmap src, Bitmap watermark) {
		String tag = "createBitmap";
		Log.d(tag, "create a new bitmap");
		if (src == null) {
			return null;
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建�?个新的和SRC长度宽度�?样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �? 0�?0坐标�?始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * create the bitmap from a byte array
	 * 
	 * @param src
	 *            the bitmap object you want proecss
	 * @param watermark
	 *            the water mark above the src
	 * @return return a bitmap object ,if paramter's length is 0,return null
	 */
	public static Bitmap createBitmap1(Bitmap src, Bitmap watermark) {
		String tag = "createBitmap";
		Log.d(tag, "create a new bitmap");
		if (src == null) {
			return null;
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();

		if (h < wh) {
			Point point = BitmapUtil.getScaledHeightSize(h, ww, wh);
			ww = point.x;
			wh = point.y;
			watermark = lessenBitmap(watermark, point.x, point.y);
		}

		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建�?个新的和SRC长度宽度�?样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �? 0�?0坐标�?始画入src
		// draw watermark into
		cv.drawBitmap(watermark, (w - ww) / 2, (h - wh) / 2, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * lessen the bitmap
	 * 
	 * @param src
	 *            bitmap
	 * @param destWidth
	 *            the dest bitmap width
	 * @param destHeigth
	 * @return new bitmap if successful ,oherwise null
	 */
	public static Bitmap lessenBitmap(Bitmap src, int destWidth, int destHeigth) {
		String tag = "lessenBitmap";
		if (src == null) {
			return null;
		}
		int w = src.getWidth();// 源文件的大小
		int h = src.getHeight();
		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) destWidth) / w;// 宽度缩小比例
		float scaleHeight = ((float) destHeigth) / h;// 高度缩小比例

		Matrix m = new Matrix();// 矩阵
		m.postScale(scaleWidth, scaleHeight);// 设置矩阵比例
		Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// 直接按照矩阵的比例把源文件画入进�?
		return resizedBitmap;
	}

	public static Bitmap getBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {

		int ivWidth = reqWidth;
		int ivHeight = reqHeight;
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();
		// if (bmpWidth < reqWidth && bmpHeight < reqHeight) {
		// return bitmap;
		// }
		// 瀹藉拰楂樼殑姣斾�?
		float scaleWidth = (float) ivWidth / bmpWidth;
		float scaleHeight = (float) ivHeight / bmpHeight;
		float scale = scaleWidth - scaleHeight > 0 ? scaleHeight : scaleWidth;
		if (scaleWidth == 0)
			scale = scaleHeight;
		if (scaleHeight == 0)
			scale = scaleWidth;
		if (scaleWidth == scaleHeight & scaleWidth == 0) {
			scale = 1;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Log.d("data", scale + " scale");
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, true);
		/*
		 * if(bitmap.getWidth() >= bitmap.getHeight()) return
		 * Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true); else
		 * return bitmap;
		 */
		return resizeBmp;

	}

	public static Bitmap getBitmap(Bitmap bitmap, float scale) {

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;

	}

	public static Bitmap getBitmapFromHeight(Bitmap bitmap, int reqWidth,
			int reqHeight) {

		int ivWidth = reqWidth;
		int ivHeight = reqHeight;
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();
		// if (bmpWidth < reqWidth && bmpHeight < reqHeight) {
		// return bitmap;
		// }

		Point sizPoint = BitmapUtil.getScaledHeightSize(reqHeight, bmpWidth,
				bmpHeight);

		//
		// float scaleWidth = (float) ivWidth / bmpWidth;
		// float scaleHeight = (float) ivHeight / bmpHeight;
		// float scale = scaleWidth - scaleHeight > 0 ? scaleHeight :
		// scaleWidth;
		// if(scaleWidth==0) scale=scaleHeight;
		// if(scaleHeight==0) scale=scaleWidth;
		// if(scaleWidth==scaleHeight&scaleWidth==0){
		// scale=1;
		// }
		// Matrix matrix = new Matrix();
		// matrix.postScale(scale, scale);
		// Log.d("data", scale+" scale");
		Matrix matrix = new Matrix();
		float scale = sizPoint.y / bmpHeight;
		matrix.postScale(scale, scale);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
				bmpHeight, matrix, true);
		/*
		 * if(bitmap.getWidth() >= bitmap.getHeight()) return
		 * Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true); else
		 * return bitmap;
		 */
		return resizeBmp;

	}

	public static Bitmap big(Bitmap b, float x, float y) {
		int w = b.getWidth();
		int h = b.getHeight();
		float sx = (float) x / w;// 要强制转换，不转换我的在这总是死掉。
		float sy = (float) y / h;
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
		return resizeBmp;
	}

	public static Bitmap decodeBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static void recycle(ViewGroup layout, boolean flagWithBackgroud) {

		for (int i = 0; i < layout.getChildCount(); i++) {
			// 获得该布局的所有子布局
			View subView = layout.getChildAt(i);
			// 判断子布局属性，如果还是ViewGroup类型，递归回收
			if (subView instanceof ViewGroup) {
				// 递归回收
				recycle((ViewGroup) subView, true);
			} else {
				// 是Imageview的子例
				if (subView instanceof ImageView) {
					// 回收占用的Bitmap
					recycleImageViewBitMap((ImageView) subView);
					// 如果flagWithBackgroud为true,则同时回收背景图
					if (flagWithBackgroud) {

						recycleBackgroundBitMap((ImageView) subView);
					}
				}
			}
		}
	}

	public static void recycleBackgroundBitMap(ImageView view) {
		if (view != null) {
			Drawable drawable = view.getBackground();
			if (drawable == null)
				return;
			view.setImageBitmap(null);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				view.setBackground(null);
			} else {
				view.setBackgroundDrawable(null);
			}

			BitmapDrawable bd = null;
			// AnimationDrawable
			if (drawable instanceof BitmapDrawable) {
				bd = (BitmapDrawable) drawable;
			}
			rceycleBitmapDrawable(bd);
		}
	}

	public static void recycleViewBitMap(View view) {
		if (view != null) {
			Drawable drawable = view.getBackground();
			if (drawable == null)
				return;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				view.setBackground(null);
			} else {
				view.setBackgroundDrawable(null);
			}
			BitmapDrawable bd = null;
			// AnimationDrawable
			if (drawable instanceof BitmapDrawable) {
				bd = (BitmapDrawable) drawable;
			}
			rceycleBitmapDrawable(bd);
		}
	}

	public static void recycleImageViewBitMap(ImageView imageView) {
		if (imageView != null) {

			Drawable drawable = imageView.getDrawable();
			if (drawable == null)
				return;
			imageView.setImageBitmap(null);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				imageView.setBackground(null);
			} else {
				imageView.setBackgroundDrawable(null);
			}
			BitmapDrawable bd = null;
			// AnimationDrawable
			if (drawable instanceof BitmapDrawable) {
				bd = (BitmapDrawable) drawable;
			}
			rceycleBitmapDrawable(bd);

		}
	}

	public static void rceycleBitmapDrawable(BitmapDrawable bd) {
		if (bd != null) {
			if (bd == null)
				return;
			Bitmap bitmap = bd.getBitmap();
			recycleBitmap(bitmap);
		}
		bd = null;
	}

	public static void recycleBitmap(Bitmap bitmap) {
		try {
			if (bitmap == null)
				return;
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
				AbLogUtil.d("recycleBitmap", "recycleBitmap");
			}
			// System.gc();
		} catch (Exception e) {
			AbLogUtil.e("recycleBitmap", e.getMessage().toString());
		}

	}

	public static void recycleBitmap(ImageView imageView) {
		if (imageView == null)
			return;
		BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView
				.getDrawable();
		if (bitmapDrawable == null)
			return;
		imageView.setImageBitmap(null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			imageView.setBackground(null);
		} else {
			imageView.setBackgroundDrawable(null);
		}
		Bitmap bitmap = bitmapDrawable.getBitmap();
		recycleBitmap(bitmap);
		// 然后再显示新的图片

	}
	
	public static void recycleAnimationBitmap(ImageView imageView) {
		if (imageView == null)
			return;
		AnimationDrawable bitmapDrawable = (AnimationDrawable) imageView
				.getDrawable();
		if (bitmapDrawable == null)
			return;
		imageView.setImageBitmap(null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			imageView.setBackground(null);
		} else {
			imageView.setBackgroundDrawable(null);
		}
		RecycleAnimationDrawable(bitmapDrawable);
		//bitmapDrawable = null;
		//recycleBitmap(bitmap);
		// 然后再显示新的图片

	}
	public static void RecycleAnimationDrawable(AnimationDrawable animationDrawable) {
		if (animationDrawable != null) {
	        animationDrawable.stop();
	        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
	            Drawable frame = animationDrawable.getFrame(i);
	            if (frame instanceof BitmapDrawable) {
	                ((BitmapDrawable) frame).getBitmap().recycle();
	            }
	            frame.setCallback(null);
	        }
	        animationDrawable.setCallback(null);
	    }
	}



	public static synchronized BitmapFactory.Options getDecodeBitmapOptions(
			String filename, int reqWidth, int reqHeight, int s) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		// options.inSampleSize = calculateInSampleSize(options, reqWidth,
		// reqHeight);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight) * s;
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		return options;
		// return getBitmapThumbnails(BitmapFactory.decodeFile(filename,
		// options),
		// reqWidth, reqHeight);
	}

	public static Drawable ZoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(null, newbmp);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

}

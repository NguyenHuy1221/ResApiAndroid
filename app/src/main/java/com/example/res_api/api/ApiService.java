package com.example.res_api.api;

import com.example.res_api.Model.Category;
import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.ChiTietHoaDon;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.GioHang;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.Model.Products;
import com.example.res_api.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // link Api:http://localhost:3000/api/user/showUser?API_KEY=42334ca8-99e0-4935-91d7-ee568d5b3f6a
    // link Api: https://imp-model-widely.ngrok-free.app/api/user/showUser?API_KEY=42334ca8-99e0-4935-91d7-ee568d5b3f6a

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM--dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://imp-model-widely.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    // show user
    @GET("api/user/showUser")
    Call<List<User>> converstUser(@Query("API_KEY") String API_KEY);

    // show user ID
    @GET("api/user/showUserID/{userId}")
    Call<User>showUserId(@Path("userId") String userId, @Query("API_KEY") String API_KEY);

    // show hoa don id user
    @GET("api/hoaDon/showHDID/{userId}")
    Call<List<HoaDon>>showHdIdUser(@Path("userId") String userId, @Query("API_KEY") String API_KEY);

    // show cthd id hoa don
    @GET("api/CTHD/showCthdIDhd/{hoaDonID}")
    Call<List<ChiTietHoaDon>>showCTHDidHD(@Path("hoaDonID") String hoaDonID, @Query("API_KEY") String API_KEY);


    // show Products
    @GET("api/products/showProducts")
    Call<List<Products>> productsListApi(@Query("API_KEY") String API_KEY);



    //show category
    // show CTGH
    @GET("api/category/showCategory")
    Call<List<Category>> showCategory(@Query("API_KEY") String API_KEY);

    // show ChiTietSanPham
    @GET("api/CTSP/showCTSP")
    Call<List<ChiTietSanPham>> chiTietSanPhamListApi(@Query("API_KEY") String API_KEY);

    // show Products
    @GET("api/products/showProductsCTSPID/{idChiTietSanPham}")
    Call<ChiTietSanPham> chitietsanphamID(@Path("idChiTietSanPham") String idChiTietSanPham, @Query("API_KEY") String API_KEY);

    // show Products
    @GET("api/products/showProductsID/{id}")
    Call<Products> showProductsID(@Path("id") String id, @Query("API_KEY") String API_KEY);


    // show CTGH
    @GET("api/CTGH/showCTGH")
    Call<List<ChiTietGioHang>> showChiTietGioHangList(@Query("API_KEY") String API_KEY);

    // show CTGH ID Cart
    @GET("api/CTGH/showCtghIdcart/{gioHangID}")
    Call<List<ChiTietGioHang>> showCtghIDCart(@Path("gioHangID") String gioHangID, @Query("API_KEY") String API_KEY);

    // show CTGH by Product ID
    @GET("api/CTGH/showCTGHByProductId/{productId}")
    Call<List<ChiTietGioHang>> getChiTietGioHangByProductId(@Path("productId") String productId, @Query("API_KEY") String API_KEY);


    // show CTGH ID
    @GET("api/CTGH/showCTGHid/{CTGHID}")
    Call<List<ChiTietGioHang>> getChiTietGioHangByID(@Path("CTGHID") String CTGHID, @Query("API_KEY") String API_KEY);


    // show Cart
    @GET("api/cart/showGH")
    Call<List<GioHang>> showGiohangListAPI(@Query("API_KEY") String API_KEY);

    // show Cart User
    @GET("api/cart/showGhUser/{userId}")
    Call<List<GioHang>> showGioHangUser(@Path("userId") String userId, @Query("API_KEY") String API_KEY);
    // add User
    @POST("api/user/addUser")
    Call<User> creatUser(@Body User user);

    // add Cart
    @POST("api/cart/addCart")
    Call<GioHang> creatCart(@Body GioHang gioHang);

    // add CTGH
    @POST("api/CTGH/addCTGH")
    Call<ChiTietGioHang> creatChiTietGioHangList(@Body ChiTietGioHang chiTietGioHang);

    // add Products
    @POST("api/products/addProducts")
    Call<Products> creatProducts(@Body Products products);


    // delete CTGH
    @DELETE("api/CTGH/deleteCTGH/{giohangID}")
    Call<ChiTietGioHang> deleteCTGH(@Path("giohangID") String giohangID);

    // check cart
    @GET("api/cart/getCartByUser/{userId}")
    Call<GioHang> getCartByUser(@Path("userId") String userId);

    @PUT("api/CTGH/updateCTGH/{CTGHID}")
    Call<ChiTietGioHang> updateChiTietGioHang(@Path("CTGHID") String CTGHID, @Body ChiTietGioHang chiTietGioHang);


    // add Hoa Don
    @POST("api/hoaDon/addHoaDon")
    Call<HoaDon> creatHoaDon(@Body HoaDon hoaDon);

   // add Hoa Don
    @POST("api/CTHD/addCTHD")
    Call<ChiTietHoaDon> creatCTHD(@Body ChiTietHoaDon chiTietHoaDon);


    // delete CTGHID
    @DELETE("api/CTGH/deleteCTGHid/{giohangID}")
    Call<ChiTietGioHang> deleteCTGHID(@Path("giohangID") String giohangID);

    // forgot passs
    @GET("api/user/forgotpass/{userId}")
    Call<User>forgotpass(@Path("userId") String userId);


    // show HD
    @GET("api/cart/showGH")
    Call<List<HoaDon>> showHD(@Query("API_KEY") String API_KEY);



    @GET("api/products/find")
    Call<List<Products>> findProducts(@Query("ten") String ten);



    @Multipart
    @POST("api/media/creatProductsMulte")
    Call<Products> creatProductsMulti(
            @Query("ten") String ten,
            @Query("donGiaNhap") double donGiaNhap,
            @Query("donGiaBan") double donGiaBan,
            @Query("soLuongNhap") int soLuongNhap,
            @Query("soLuongDaBan") int soLuongDaBan,
            @Query("category") String category,
            @Query("moTa") String moTa,
            @Query("tinhTrang") String tinhTrang,
               @Part MultipartBody.Part filepart
    );

}

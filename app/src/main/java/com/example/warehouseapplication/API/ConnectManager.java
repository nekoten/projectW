package com.example.warehouseapplication.API;

import com.example.warehouseapplication.Callback.CompleteCallback;
import com.example.warehouseapplication.Callback.DriverOrderCallback;
import com.example.warehouseapplication.Callback.EditCallback;
import com.example.warehouseapplication.Callback.LoginCallback;
import com.example.warehouseapplication.Callback.Login_DCallback;
import com.example.warehouseapplication.Callback.MenuCallback;
import com.example.warehouseapplication.Callback.MyDeliveryCallback;
import com.example.warehouseapplication.Callback.OrderCallback;
import com.example.warehouseapplication.Callback.OrderListCallback;
import com.example.warehouseapplication.Callback.OrderList_ItemCallback;
import com.example.warehouseapplication.Callback.ProductCallback;
import com.example.warehouseapplication.Callback.PromotionCallback;
import com.example.warehouseapplication.Callback.RegisterCallback;
import com.example.warehouseapplication.Callback.ResponseCallback;
import com.example.warehouseapplication.Callback.StockCallback;
import com.example.warehouseapplication.Callback.StockDetailCallback;
import com.example.warehouseapplication.Callback.StoreCallback;
import com.example.warehouseapplication.Callback.TakeDeliveryCallback;
import com.example.warehouseapplication.Callback.TopupListCallback;
import com.example.warehouseapplication.Callback.UpdateStockCallback;
import com.example.warehouseapplication.Model.CompleteModel;
import com.example.warehouseapplication.Model.DeliveryOrderModel;
import com.example.warehouseapplication.Model.DriverModel;
import com.example.warehouseapplication.Model.EditResponse;
import com.example.warehouseapplication.Model.MyDeliverDriver;
import com.example.warehouseapplication.Model.OrderResponse;
import com.example.warehouseapplication.Model.Orderlist;
import com.example.warehouseapplication.Model.Orderlist_item;
import com.example.warehouseapplication.Model.Product;
import com.example.warehouseapplication.Model.ProductModel;
import com.example.warehouseapplication.Model.PromotionModel;
import com.example.warehouseapplication.Model.Regis;
import com.example.warehouseapplication.Model.ResponseModel;
import com.example.warehouseapplication.Model.StockDetailModel;
import com.example.warehouseapplication.Model.StockModel;
import com.example.warehouseapplication.Model.StoreModel;
import com.example.warehouseapplication.Model.TakeDelivery;
import com.example.warehouseapplication.Model.TopupModel;
import com.example.warehouseapplication.Model.UpdateStockResponse;
import com.example.warehouseapplication.Model.User;
import com.example.warehouseapplication.Tool.Utils;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Killy77 on 14/4/2561.
 */

public class ConnectManager {
    String API = Utils.ipHost;

    public ConnectManager() {

    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService con = retrofit.create(APIService.class);

    public void login(final LoginCallback listener, String user, String pass, String token) {
        Call call = con.postLogin(user, pass,token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user = response.body();
                if (user == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(user, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void loginDriver(final Login_DCallback listener, String user, String pass, String token) {
        Call call = con.loginDriver(user, pass,token);
        call.enqueue(new Callback<DriverModel>() {
            @Override
            public void onResponse(Response<DriverModel> response, Retrofit retrofit) {
                DriverModel driver = response.body();
                if (driver == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(driver, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void takediliver(final TakeDeliveryCallback listener, String id_order, String id_emp) {
        Call call = con.acceptDelivery(id_order,id_emp);
        call.enqueue(new Callback<TakeDelivery>() {
            @Override
            public void onResponse(Response<TakeDelivery> response, Retrofit retrofit) {
                TakeDelivery deliver = response.body();
                if (deliver == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(deliver, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void myDeliver(final MyDeliveryCallback listener, String id_emp) {
        Call call = con.myDeliver(id_emp);
        call.enqueue(new Callback<MyDeliverDriver>() {
            @Override
            public void onResponse(Response<MyDeliverDriver> response, Retrofit retrofit) {
                MyDeliverDriver mydeliver = response.body();
                if (mydeliver == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(mydeliver, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void driver_complete(final CompleteCallback listener, String id_order, String id_status) {
        Call call = con.complete_driver(id_order,id_status);
        call.enqueue(new Callback<CompleteModel>() {
            @Override
            public void onResponse(Response<CompleteModel> response, Retrofit retrofit) {
                CompleteModel mydeliver = response.body();
                if (mydeliver == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(mydeliver, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }



    public void register(final RegisterCallback listener, String name, String lastname, String email, String password, String tel, String lat) {
        Call call = con.postRegister(name, lastname,email,password,tel,lat);
        call.enqueue(new Callback<Regis>() {
            @Override
            public void onResponse(Response<Regis> response, Retrofit retrofit) {
                Regis regis = response.body();
                if (regis == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(regis, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }
    public void getmenu(final MenuCallback listener) {
        Call call = con.getMenu();
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Response<Product> response, Retrofit retrofit) {
                Product product = response.body();
                if (product == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(product, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }
    public void getstore(final StoreCallback listener) {
        Call call = con.getStore();
        call.enqueue(new Callback<StoreModel>() {
            @Override
            public void onResponse(Response<StoreModel> response, Retrofit retrofit) {
                StoreModel store = response.body();
                if (store == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(store, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getstock(final StockCallback listener, String id_stock) {
        Call call = con.getStock(id_stock);
        call.enqueue(new Callback<StockModel>() {
            @Override
            public void onResponse(Response<StockModel> response, Retrofit retrofit) {
                StockModel stock = response.body();
                if (stock == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(stock, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void getstockDetail(final StockDetailCallback listener, String id_stock) {
        Call call = con.getStockDetail(id_stock);
        call.enqueue(new Callback<StockDetailModel>() {
            @Override
            public void onResponse(Response<StockDetailModel> response, Retrofit retrofit) {
                StockDetailModel stock = response.body();
                if (stock == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(stock, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }


    public void updateStatusType(final ResponseCallback listener, String id_order, String status_type) {
        Call call = con.updateStatusType(id_order,status_type);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Response<ResponseModel> response, Retrofit retrofit) {
                ResponseModel responseModel = response.body();
                if (responseModel == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(responseModel, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void acceptMember(final ResponseCallback listener, String id_order, String id_status) {
        Call call = con.acceptMember(id_order,id_status);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Response<ResponseModel> response, Retrofit retrofit) {
                ResponseModel responseModel = response.body();
                if (responseModel == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(responseModel, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getProduct(final ProductCallback listener, String id_product) {
        Call call = con.getProduct(id_product);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Response<ProductModel> response, Retrofit retrofit) {
                ProductModel product = response.body();
                if (product == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(product, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void getPromotion(final PromotionCallback listener, String id_promotion) {
        Call call = con.getPromotion(id_promotion);
        call.enqueue(new Callback<PromotionModel>() {
            @Override
            public void onResponse(Response<PromotionModel> response, Retrofit retrofit) {
                PromotionModel promotion = response.body();
                if (promotion == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(promotion, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void updatestock(final UpdateStockCallback listener, String id_stock , String amount, String exp) {
        Call call = con.updateStock(id_stock,amount,exp);
        call.enqueue(new Callback<UpdateStockResponse>() {
            @Override
            public void onResponse(Response<UpdateStockResponse> response, Retrofit retrofit) {
                UpdateStockResponse stock = response.body();
                if (stock == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(stock, retrofit);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void order(final OrderCallback listener, String order) {
        Call call = con.order(order);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Response<OrderResponse> response, Retrofit retrofit) {
                OrderResponse orderResponse = response.body();
                if (orderResponse == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(orderResponse, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void orderList(final OrderListCallback listener, String id_member) {
        Call call = con.orderlist_member(id_member);
        call.enqueue(new Callback<Orderlist>() {
            @Override
            public void onResponse(Response<Orderlist> response, Retrofit retrofit) {
                Orderlist orderlist = response.body();
                if (orderlist == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(orderlist, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public void orderList_item(final OrderList_ItemCallback listener, String id_order) {
        Call call = con.orderlistItem(id_order);
        call.enqueue(new Callback<Orderlist_item>() {
            @Override
            public void onResponse(Response<Orderlist_item> response, Retrofit retrofit) {
                Orderlist_item orderlist_item = response.body();
                if (orderlist_item == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(orderlist_item, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public void edit_Account(final EditCallback listener, String firstname
            , String lastname
            , String email
            , String tel
            , String id_member) {
        Call call = con.editAccount(firstname,lastname,email,tel,id_member);
        call.enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Response<EditResponse> response, Retrofit retrofit) {
                EditResponse edit = response.body();
                if (edit == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(edit, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }
    public void edit_Account_driver(final EditCallback listener, String firstname
            , String lastname
            , String id_emp) {
        Call call = con.editAccount_driver(firstname,lastname,id_emp);
        call.enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Response<EditResponse> response, Retrofit retrofit) {
                EditResponse edit = response.body();
                if (edit == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(edit, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }
    public void getTopup(final TopupListCallback listener, String id_member) {
        Call call = con.getTopupstatus(id_member);
        call.enqueue(new Callback<TopupModel>() {
            @Override
            public void onResponse(Response<TopupModel> response, Retrofit retrofit) {
                TopupModel topup = response.body();
                if (topup == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(topup, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });

    }
    public void getAllOrder(final DriverOrderCallback listener) {
        Call call = con.getDriverorder();
        call.enqueue(new Callback<DeliveryOrderModel>() {
            @Override
            public void onResponse(Response<DeliveryOrderModel> response, Retrofit retrofit) {
                DeliveryOrderModel topup = response.body();
                if (topup == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse(topup, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }


        });

    }
}

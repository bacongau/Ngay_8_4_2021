package com.example.ngay_8_4_2021.model.response;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ResponseChiTietSanPham {
    private int code;
    private String msg;
    private ChiTietSanPham data;

    public ResponseChiTietSanPham() {
    }

    public ResponseChiTietSanPham(int code, String msg, ChiTietSanPham data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChiTietSanPham getData() {
        return data;
    }

    public void setData(ChiTietSanPham data) {
        this.data = data;
    }


    public class ChiTietSanPham{
        private String id;
        private String name;
        private int price;
        private String description;
        private float createdDate;
        private String logoUrl;
        private CategoryClothes category;
        private ArrayList<RateClothesViewModels> rateClothesViewModels;
        private int numberSave;
        private boolean isSaved;
        private float avarageOfRate;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String vnd = currencyVN.format(price);

            return vnd;
        }

        public String getDescription() {
            return description;
        }

        public float getCreatedDate() {
            return createdDate;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public CategoryClothes getCategory() {
            return category;
        }

        public ArrayList<RateClothesViewModels> getRateClothesViewModels() {
            return rateClothesViewModels;
        }

        public int getNumberSave() {
            return numberSave;
        }

        public boolean isSaved() {
            return isSaved;
        }

        public float getAvarageOfRate() {
            return avarageOfRate;
        }

        public class CategoryClothes{
            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }
        }

        public class RateClothesViewModels{
            private String customerName;
            private String logoUrl;
            private float rateDate;
            private String message;
            private int rating;

            public String getCustomerName() {
                return customerName;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public float getRateDate() {
                return rateDate;
            }

            public String getMessage() {
                return message;
            }

            public int getRating() {
                return rating;
            }
        }
    }
}

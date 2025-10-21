package RMI;

import java.io.Serializable;

public class ProductX implements Serializable {
    private String code;
    private String discountCode;
    private int discount;
    // ✅ Sửa lại cho khớp với server
    private static final long serialVersionUID = 20171107L; 

    public ProductX() {}
    public ProductX(String code, String discountCode, int discount) {
        this.code = code;
        this.discountCode = discountCode;
        this.discount = discount;
    }

    public String getCode() { return code; }
    public String getDiscountCode() { return discountCode; }
    public int getDiscount() { return discount; }

    public void setCode(String code) { this.code = code; }
    public void setDiscountCode(String discountCode) { this.discountCode = discountCode; }
    public void setDiscount(int discount) { this.discount = discount; }

    @Override
    public String toString() {
        return "ProductX{code='" + code + "', discountCode='" + discountCode + "', discount=" + discount + "}";
    }
}

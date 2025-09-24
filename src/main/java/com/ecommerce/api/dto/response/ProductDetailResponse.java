package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductDetailResponse {

  private List<ProductDetail> productDetails = new ArrayList<>();

  @Getter
  @Setter
  public static class ProductDetail{
      private Integer id;
      private String name;
      private String description;
      private BigDecimal price;
      private Integer stock;
  }
}

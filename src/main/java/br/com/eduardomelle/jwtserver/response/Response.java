package br.com.eduardomelle.jwtserver.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Response<T> {

  private T data;

  private List<String> errors = new ArrayList<>();

}

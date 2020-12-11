package com.example.springnetty.filter;

import com.example.springnetty.netty.Exception.TuulException;

public interface ITuulFilter {
  void run() throws TuulException;
}

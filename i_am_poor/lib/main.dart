import 'package:flutter/material.dart';

void main() {
  runApp(
    MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('I am Poor'),
          backgroundColor: Colors.blueGrey[50],
        ),
        body: Center(
          child: Image(
            image: AssetImage('images/poor.jpg'),
          ),
//            image: NetworkImage(
//                'https://ichef.bbci.co.uk/news/1024/media/images/59211000/jpg/_59211553_59211551.jpg'),
//          ),
        ),
      ),
    ),
  );
}

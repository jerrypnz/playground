#![feature(custom_derive, plugin)]
#![plugin(serde_macros)]

extern crate serde_json;
extern crate chrono;

use chrono::*;

//#[derive(Serialize, Deserialize, Debug)]
//#[allow(non_snake_case)]
//struct Item {
//    itemId: String,
//    description: String,
//    itemCategoryId: String,
//}
//
//fn test_item() {
//    let item = Item {
//        itemId: "foo".to_string(),
//        description: "test".to_string(),
//        itemCategoryId: "foo".to_string(),
//    };
//
//    let json = serde_json::to_string(&item).unwrap();
//
//    println!("{}", json);
//
//    let item1: Item = serde_json::from_str(&json).unwrap();
//
//    println!("{:?}", item1);
//}

#[derive(Serialize, Deserialize, Debug)]
struct FunkyValue {
    foo: String,
    bar: i64,
}

#[derive(Serialize, Deserialize, Debug)]
#[allow(non_camel_case_types)]
enum MetricValue {
    int(i64),
    string(String),
    double(f64),
    funky(FunkyValue),
    utcDateTime(DateTime<UTC>),
    localDateTime(NaiveDateTime),
}

#[derive(Serialize, Deserialize, Debug)]
struct Metric {
    id: String,
    value: MetricValue,
}

fn test_metric() {
    let metric1 = Metric {
        id: "foo1".to_string(),
        value: MetricValue::string("foobar".to_string()),
    };

    let metric2 = Metric {
        id: "foo2".to_string(),
        value: MetricValue::int(42),
    };

    let metric3 = Metric {
        id: "foo2".to_string(),
        value: MetricValue::funky(FunkyValue {
            foo: "foobar".to_string(),
            bar: 2001,
        }),
    };

    let metric4 = Metric {
        id: "foo2".to_string(),
        value: MetricValue::utcDateTime(UTC::now()),
    };

    let metric5 = Metric {
        id: "foo2".to_string(),
        value: MetricValue::localDateTime(Local::now().naive_local()),
    };

    let json1 = serde_json::to_string(&metric1).unwrap();
    let json2 = serde_json::to_string(&metric2).unwrap();
    let json3 = serde_json::to_string(&metric3).unwrap();
    let json4 = serde_json::to_string(&metric4).unwrap();
    let json5 = serde_json::to_string(&metric5).unwrap();

    println!("{}", json1);
    println!("{}", json2);
    println!("{}", json3);
    println!("{}", json4);
    println!("{}", json5);
}

fn main() {
    test_metric();
}

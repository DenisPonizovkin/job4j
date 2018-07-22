select * from query.product as p inner join query.type as t on p.type_id = t.id and t.name = 'СЫР';
select * from query.product where name like '%мороженое%';
select * from query.product where extract(month from query.product.expired_date) - extract(month from now()) = 1
select *  from  product p inner join (select max(price) from product) price on p.price=price
select count(*) from product where type_id = 1
select * from query.product as p inner join query.type as t on p.type_id = t.id and t.name = 'СЫР'and t.name = 'МОЛОКО';
select distinct(id) from product p inner join (SELECT type_id, COUNT(*) FROM product GROUP BY type_id) cnt on cnt.count < 10 and p.type_id = cnt.type_id
select *, t.id from product p inner join (select * from type) t on p.type_id = t.id

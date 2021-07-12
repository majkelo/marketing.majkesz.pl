# marketing.majkesz.pl
API for marketing endpoints; recruitment purposes


### Configuration (SDKMAN recommended)
sdk default grails 4.0.11;
sdk default gradle 3.5.1;
sdk default groovy 2.5.7;


### OpenAPI docs and queries testing
https://majkesz-pl.stoplight.io/docs/marketing-majkesz-pl


### DB (dev console on localhost)
http://localhost:8080/h2-console/
Double check if you've provided a proper JDBC URL: `jdbc:h2:mem:devDb`


### Example queries

- Total ​ Clicks ​ for a given ​ Datasource ​ for a given ​ Date ​ range
/click?datasource=1&startDate=2019-11-10&endDate=2019-11-12&count=true

- All listed ​ Clicks ​ for a given ​ Datasource ​ for a given ​ Date ​ range
/click?datasource=1&startDate=2019-11-10&endDate=2019-11-12

- Click-Through Rate (CTR) ​ per ​ Datasource ​ and ​ Campaign
/ctr?datasource=1&campaign=1

- Impressions ​ over time (daily)
/impression?countPerDay=day

- Clicks sorted descending basing on date ​in a specific timeperiod
/click?sort=date&order=desc&startDate=2019-11-12&endDate=2019-11-12

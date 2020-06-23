import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {RestClientService} from '../services/rest-client.service';
import {AppCookieService} from '../AppCookieService/app-cookie-service.service';
import {ConstantsModel} from '../util/constants-model';
import {CovidDataModel} from '../model/covid-data-model';
import {Table} from 'primeng/table';

@Component({
  selector: 'app-covid-home',
  templateUrl: './covid-home.component.html',
  styleUrls: ['./covid-home.component.css']
})
export class CovidHomeComponent implements OnInit {
  @ViewChild('tt', { static: false }) public table: Table;

  @Input() regionName: string;
  @Input() parentRegion: string;
  @Input() regionType: string;
  private tokenString: string;

  // Data to display in UI - Aggregated Numbers Card
  private numberCardList: any [];

  // Data to display all the child entities - List of states or districts
  private allChildRegions: any [];

  // Data to display all the child entities in total percentage - List of states or districts
  private topHitRegions: any [];

  // Detailed date wise data for country/state/district - Line chart
  private cumulativeData: any[];

  colorScheme = {
    domain: ['#9400D3', '#E44D25', '#006400', '#890202']
  };
  colorSchemeLineChart = {
    domain: ['#E44D25', '#006400', '#890202']
  };
  cardColor = '#232837';
  subRegions: CovidDataModel[];
  filterString: string;

  constructor(private service: RestClientService, private cookie: AppCookieService) {
  }

  ngOnInit() {
    this.filterString = '';
    this.tokenString = this.cookie.get(ConstantsModel.SECTRET_KEY);
    this.loadDashBoard();
  }

  private loadDashBoard() {
    this.showNumberChart(this.regionName);
    this.loadCumulativeData(this.regionName, this.regionType);
    this.showAllChildRegionTypes(this.regionName, this.regionType, this.parentRegion);
  }

  private showAllChildRegionTypes(name, regionType, parentRegion) {
    const resp = this.service.getAggregatedDataSubRegion(this.tokenString, name, regionType);
    resp.subscribe(data => {
      this.subRegions = JSON.parse(data.toString()) as CovidDataModel[];
      this.subRegions.sort((a, b) => {
        if (a.confirmed > b.confirmed) {
          return -1;
        } else {
          return +1;
        }
      });
      let maxRegion = 0;
      this.allChildRegions = [];
      this.subRegions.forEach(listItem => {
        if (maxRegion < 20) {
          this.allChildRegions.push({
            name: listItem.regionName,
            series: [
              {
                name: ConstantsModel.ACTIVE,
                value: listItem.active
              },
              {
                name: ConstantsModel.RECOVERED,
                value: listItem.recovered
              }, {
                name: ConstantsModel.DECEASED,
                value: listItem.deceased
              }
            ]
          });
          maxRegion++;
        }
      });
      this.topHitRegions = [];
      this.subRegions.forEach(listItem1 => {
        this.topHitRegions.push(
          {
            name: listItem1.regionName,
            value: listItem1.confirmed
          });
      });
    });
  }

  private showNumberChart(name) {
    const resp = this.service.getAggregatedDataSingleEntity(this.tokenString, name);
    resp.subscribe(data => {
      const list = JSON.parse(data.toString()) as CovidDataModel[];
      this.numberCardList = [];
      // this.numberCardList.push({"name": "Confirmed", "value": list[0].confirmed});
      this.numberCardList.push({
          name: ConstantsModel.CONFIRMED,
          value: list[0].confirmed
        },
        {
          name: ConstantsModel.ACTIVE,
          value: list[0].active
        },
        {
          name: ConstantsModel.RECOVERED,
          value: list[0].recovered
        },
        {
          name: ConstantsModel.DECEASED,
          value: list[0].deceased
        });
    });
  }

  private loadCumulativeData(name, regiontype) {
    const resp = this.service.getCumulativeData(this.tokenString, name, regiontype);
    resp.subscribe(data => {
      const list = JSON.parse(data.toString()) as CovidDataModel[];
      list.sort((a, b) => {
        return new Date(a.date).getTime() - new Date(b.date).getTime();
      });
      this.cumulativeData = [];
      const confirmedSeries: any[] = [];
      list.forEach(listItemConfirmed => {
        const date = new Date(listItemConfirmed.date);
        confirmedSeries.push({
          name: date.getMonth() + '/' + date.getDate(),
          value: listItemConfirmed.confirmed
        });
      });
      this.cumulativeData.push({
        name: ConstantsModel.CONFIRMED,
        series: confirmedSeries
      });
      const activeSeries: any[] = [];
      list.forEach(listItemConfirmed => {
        const date = new Date(listItemConfirmed.date);
        activeSeries.push({
          name: date.getMonth() + '/' + date.getDate(),
          value: listItemConfirmed.active
        });
      });
      this.cumulativeData.push({
        name: ConstantsModel.ACTIVE,
        series: activeSeries
      });
      const recoveredSeries: any[] = [];
      list.forEach(listItemConfirmed => {
        const date = new Date(listItemConfirmed.date);
        recoveredSeries.push({
          name: date.getMonth() + '/' + date.getDate(),
          value: listItemConfirmed.recovered
        });
      });
      this.cumulativeData.push({
        name: ConstantsModel.RECOVERED,
        series: recoveredSeries
      });
      const deceasedSeries: any[] = [];
      list.forEach(listItemConfirmed => {
        const date = new Date(listItemConfirmed.date);
        deceasedSeries.push({
          name: date.getMonth() + '/' + date.getDate(),
          value: listItemConfirmed.deceased
        });
      });
      this.cumulativeData.push({
        name: ConstantsModel.DECEASED,
        series: deceasedSeries
      });
    });
  }

  refreshDashBoardWithNewData(regionName, regionType, parentRegion) {
    this.regionType = regionType;
    this.regionName = regionName;
    this.parentRegion = parentRegion;
    this.ngOnInit();
  }
}

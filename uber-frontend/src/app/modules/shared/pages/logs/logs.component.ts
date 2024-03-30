import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {map, Observable, startWith} from "rxjs";
import {ReportService} from "../../services/report.service";
import {TokenUtilsService} from "../../services/token-utils.service";
import {UserService} from "../../services/user.service";
import {DateRange} from "../../../../model/DateRange";
import {ReportResponse} from "../../../../model/ReportResponse";
import {HttpErrorResponse} from "@angular/common/http";
import { MatTableDataSource } from '@angular/material/table';
import {User} from "../../../../model/User";
import {Ride} from "../../../../model/Ride";
import {LogService} from "../../services/log/log.service";
import {Request} from "../../components/history/history.component";
import {ElasticLog} from "../../../../model/elastic-log/elasticLog";

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit{
  logTypeControl = new FormControl('');
  loggedUser: User | null;
  protected userType: string;

  logs: ElasticLog[] = [];

  displayedColumns = ['id', 'logType'];

  constructor(private logService: LogService) {
  }

  getLogsByLogType(request: Request)
  {
    this.logService.getLogsByLogType(request, this.logTypeControl.value as string).subscribe({
      next: (data: ElasticLog[]) => {
        this.logs = data
        console.log(data);
      },
      error: (err: HttpErrorResponse) => {
        console.log(err.error.message);
      },
    });
  }

  ngOnInit(): void {
    this.getLogsByLogType({ page: 0, size: 10 })
  }

}

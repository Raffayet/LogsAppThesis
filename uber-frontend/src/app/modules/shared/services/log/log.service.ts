import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Request} from "../../components/history/history.component";

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  getLogsByLogType(request: Request, logType: string): Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("format", "json");
    queryParams = queryParams.append("page", request.page);
    queryParams = queryParams.append("size", request.size);
    queryParams = queryParams.append("logType", logType);

    return this.http.get<any>(environment.apiURL + "/logs/get-logs-by-type", { params: queryParams});
  }
}

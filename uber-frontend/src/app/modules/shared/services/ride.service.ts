import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/app/environments/environment';
import { TokenUtilsService } from './token-utils.service';
import { Request } from '../components/history/history.component';
import { Ride } from 'src/app/model/Ride';

@Injectable({
  providedIn: 'root'
})
export class RideService {

  constructor(private http: HttpClient, private tokenUtilsService: TokenUtilsService) { }

  getHistoryOfRides(request: Request, email: string): Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("format", "json");
    queryParams = queryParams.append("page", request.page);
    queryParams = queryParams.append("size", request.size);
    queryParams = queryParams.append("email", email);

    return this.http.get<any>(environment.apiURL + "/rides/get-all", { params: queryParams});
  }

  getHistoryOfDriversRides(request: Request, email: string): Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("format", "json");
    queryParams = queryParams.append("page", request.page);
    queryParams = queryParams.append("size", request.size);
    queryParams = queryParams.append("email", email);

    return this.http.get<any>(environment.apiURL + "/rides/get-all-drivers-rides", { params: queryParams});
  }

  getRideByVehicleType(): Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("format", "json");

    return this.http.get<any>(environment.apiURL + "/rides/get-all-rides", { params: queryParams});
  }

  addWord(word: string): Observable<any>{
    let queryParams = new HttpParams();
    queryParams = queryParams.append("format", "json");
    queryParams = queryParams.append("word", word);

    return this.http.post<any>(environment.apiURL + "/rides/add-word", { params: queryParams});
  }
}

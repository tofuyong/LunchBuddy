import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HobbyService {

  private GET_HOBBIES_BY_EMP_URL = "api/hobby/all";
  private ADD_HOBBY_URL = "api/hobby/add";
  private DELETE_HOBBY_URL = "api/hobby/delete";

  constructor(private httpClient: HttpClient) { }

  getAllHobbies(employeeId: string): Promise<any> {
    const params = new HttpParams().set('employeeId', employeeId);
    return firstValueFrom(this.httpClient.get(this.GET_HOBBIES_BY_EMP_URL, { params, responseType: 'json' }));
  }

  addHobby(hobby: any): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.ADD_HOBBY_URL, hobby, { responseType: 'json' }));
  }

  deleteHobby(hobby: string): Promise<any> {
    const params = new HttpParams().set('hobby', hobby);
    return firstValueFrom(this.httpClient.delete(this.DELETE_HOBBY_URL, { params, responseType: 'json' }));
  } 

}

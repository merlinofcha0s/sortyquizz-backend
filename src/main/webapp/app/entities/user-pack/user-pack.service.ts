import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserPack } from 'app/shared/model/user-pack.model';

type EntityResponseType = HttpResponse<IUserPack>;
type EntityArrayResponseType = HttpResponse<IUserPack[]>;

@Injectable({ providedIn: 'root' })
export class UserPackService {
  public resourceUrl = SERVER_API_URL + 'api/user-packs';

  constructor(protected http: HttpClient) {}

  create(userPack: IUserPack): Observable<EntityResponseType> {
    return this.http.post<IUserPack>(this.resourceUrl, userPack, { observe: 'response' });
  }

  update(userPack: IUserPack): Observable<EntityResponseType> {
    return this.http.put<IUserPack>(this.resourceUrl, userPack, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserPack>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserPack[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

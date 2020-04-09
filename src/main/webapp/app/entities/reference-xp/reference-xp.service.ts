import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReferenceXP } from 'app/shared/model/reference-xp.model';

type EntityResponseType = HttpResponse<IReferenceXP>;
type EntityArrayResponseType = HttpResponse<IReferenceXP[]>;

@Injectable({ providedIn: 'root' })
export class ReferenceXPService {
  public resourceUrl = SERVER_API_URL + 'api/reference-xps';

  constructor(protected http: HttpClient) {}

  create(referenceXP: IReferenceXP): Observable<EntityResponseType> {
    return this.http.post<IReferenceXP>(this.resourceUrl, referenceXP, { observe: 'response' });
  }

  update(referenceXP: IReferenceXP): Observable<EntityResponseType> {
    return this.http.put<IReferenceXP>(this.resourceUrl, referenceXP, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferenceXP>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferenceXP[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

package br.usjt.ucsist.cadaluno.cad.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContatoService {
    @GET("/api/07eba208b4a544ffb64aafe03806d5f4/contato")
    Call<List<Contato>> getAllContatos();
    @POST("/api/07eba208b4a544ffb64aafe03806d5f4/contato")
    Call<ResponseBody> salvarContato(
            @Body
                    Contato contato);
    @PUT("/api/07eba208b4a544ffb64aafe03806d5f4/contato/{id}")
    Call<ResponseBody> alterarContato(
            @Path("id") String id,
            @Body ContatoPut contatoPut);
    @DELETE("/api/07eba208b4a544ffb64aafe03806d5f4/contato/{id}")
    Call<ResponseBody> deletarContato(
            @Path("id") String id);
}

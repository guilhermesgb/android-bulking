package com.guilhermesgb.robospiceretrofit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.storage.GuideItemsProvider;

@Table(name = "GuideItems")
public class GuideItem extends Model implements Parcelable {

    @Column private Integer version = null;
    @Column private String name = null;
    @Column private Integer guideItemId = null;
    @Column private String category = null;
    @Column private String description = null;
    @Column private String shortDescription = null;
    @Column private String email = null;
    @Column private String phone = null;
    @Column private String website = null;
    @Column private String address = null;
    @Column private String schedule = null;
    @Column private Double minCost = null;
    @Column private Double maxCost = null;
    @Column private String cost = null;
    @Column private String creditCard = null;
    @Column private String breakfast = null;
    @Column private String capacity = null;
    @Column private String metro = null;
    @Column private String tram = null;
    @Column private String bus = null;
    @Column private String plane = null;
    @Column private String boat = null;
    @Column private String car = null;
    @Column private String gondola = null;
    @Column private String train = null;
    @Column private String bike = null;
    @Column private String walking = null;

    public GuideItem() {
        super();
    }

    public GuideItem(JsonObject rawBody) {
        super();
        setVersion(rawBody);
        setName(rawBody);
        setGuideItemId(rawBody);
        setCategory(rawBody);
        setDescription(rawBody);
        setShortDescription(rawBody);
        setEmail(rawBody);
        setPhone(rawBody);
        setWebsite(rawBody);
        setAddress(rawBody);
        setSchedule(rawBody);
        setMinCost(rawBody);
        setMaxCost(rawBody);
        setCost(rawBody);
        setCreditCard(rawBody);
        setBreakfast(rawBody);
        setCapacity(rawBody);
        setMetro(rawBody);
        setTram(rawBody);
        setBus(rawBody);
        setPlane(rawBody);
        setBoat(rawBody);
        setCar(rawBody);
        setGondola(rawBody);
        setTrain(rawBody);
        setBike(rawBody);
        setWalking(rawBody);
        GuideItem self = GuideItemsProvider.retrieveStoredGuideItem(this.getGuideItemId());
        if (self != null) {
            if (this.getVersion() > self.getVersion()) {
                self.setVersion(this.getVersion());
                self.setName(this.getName());
                self.setGuideItemId(this.getGuideItemId());
                self.setCategory(this.getCategory());
                self.setDescription(this.getDescription());
                self.setShortDescription(this.getShortDescription());
                self.setEmail(this.getEmail());
                self.setPhone(this.getPhone());
                self.setWebsite(this.getWebsite());
                self.setAddress(this.getAddress());
                self.setSchedule(this.getSchedule());
                self.setMinCost(this.getMinCost());
                self.setMaxCost(this.getMaxCost());
                self.setCost(this.getCost());
                self.setCreditCard(this.getCreditCard());
                self.setBreakfast(this.getBreakfast());
                self.setCapacity(this.getCapacity());
                self.setMetro(this.getMetro());
                self.setTram(this.getTram());
                self.setBus(this.getBus());
                self.setPlane(this.getPlane());
                self.setBoat(this.getBoat());
                self.setCar(this.getCar());
                self.setGondola(this.getGondola());
                self.setTrain(this.getTrain());
                self.setBike(this.getBike());
                self.setWalking(this.getWalking());
                self.save();
            }
        }
        else {
            this.save();
        }
    }

    protected GuideItem(Parcel in) {
        name = in.readString();
        category = in.readString();
        description = in.readString();
        shortDescription = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
        address = in.readString();
        schedule = in.readString();
        cost = in.readString();
        creditCard = in.readString();
        breakfast = in.readString();
        capacity = in.readString();
        metro = in.readString();
        tram = in.readString();
        bus = in.readString();
        plane = in.readString();
        boat = in.readString();
        car = in.readString();
        gondola = in.readString();
        train = in.readString();
        bike = in.readString();
        walking = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeString(address);
        dest.writeString(schedule);
        dest.writeString(cost);
        dest.writeString(creditCard);
        dest.writeString(breakfast);
        dest.writeString(capacity);
        dest.writeString(metro);
        dest.writeString(tram);
        dest.writeString(bus);
        dest.writeString(plane);
        dest.writeString(boat);
        dest.writeString(car);
        dest.writeString(gondola);
        dest.writeString(train);
        dest.writeString(bike);
        dest.writeString(walking);
    }

    public static final Creator<GuideItem> CREATOR = new Creator<GuideItem>() {

        @Override
        public GuideItem createFromParcel(Parcel in) {
            return new GuideItem(in);
        }

        @Override
        public GuideItem[] newArray(int size) {
            return new GuideItem[size];
        }

    };

    public void setVersion(JsonObject rawBody) {
        final JsonElement version = rawBody.get("custom_fields").getAsJsonObject().get("version");
        setFieldValue(version, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setVersion(version.getAsInt());
            }
        });
        if (this.version == null) {
            this.version = 1;
        }
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Integer getVersion() {
        Integer version = this.version;
        if (version == null) {
            version = 1;
        }
        return version;
    }

    public void setName(JsonObject rawBody) {
        final JsonElement name = rawBody.get("title");
        setFieldValue(name, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setName(name.getAsString());
            }
        });
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGuideItemId(JsonObject rawBody) {
        final JsonElement id = rawBody.get("custom_fields").getAsJsonObject().get("id");
        setFieldValue(id, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setGuideItemId(id.getAsInt());
            }
        });
    }

    public void setGuideItemId(int guideItemId) {
        this.guideItemId = guideItemId;
    }

    public Integer getGuideItemId() {
        return this.guideItemId;
    }

    public void setCategory(JsonObject rawBody) {
        final JsonElement category = rawBody.get("custom_fields").getAsJsonObject().get("category");
        setFieldValue(category, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setCategory(category.getAsString());
            }
        });
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setDescription(JsonObject rawBody) {
        final JsonElement description = rawBody.get("custom_fields")
                .getAsJsonObject().get("description");
        setFieldValue(description, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setDescription(description.getAsString());
            }
        });
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setShortDescription(JsonObject rawBody) {
        final JsonElement shortDescription = rawBody.get("custom_fields")
                .getAsJsonObject().get("shortDescription");
        setFieldValue(shortDescription, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setShortDescription(shortDescription.getAsString());
            }
        });
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setEmail(JsonObject rawBody) {
        final JsonElement email = rawBody.get("custom_fields").getAsJsonObject().get("email");
        setFieldValue(email, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setEmail(email.getAsString());
            }
        });
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(JsonObject rawBody) {
        final JsonElement phone = rawBody.get("custom_fields").getAsJsonObject().get("phone");
        setFieldValue(phone, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setPhone(phone.getAsString());
            }
        });
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setWebsite(JsonObject rawBody) {
        final JsonElement website = rawBody.get("custom_fields").getAsJsonObject().get("website");
        setFieldValue(website, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setWebsite(website.getAsString());
            }
        });
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setAddress(JsonObject rawBody) {
        final JsonElement address = rawBody.get("custom_fields").getAsJsonObject().get("address");
        setFieldValue(address, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setAddress(address.getAsString());
            }
        });
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setSchedule(JsonObject rawBody) {
        final JsonElement schedule = rawBody.get("custom_fields").getAsJsonObject().get("schedule");
        setFieldValue(schedule, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setSchedule(schedule.getAsString());
            }
        });
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public void setMinCost(JsonObject rawBody) {
        final JsonElement minCost = rawBody.get("custom_fields").getAsJsonObject().get("min_cost");
        setFieldValue(minCost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setMinCost(minCost.getAsDouble());
            }
        });
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    public Double getMinCost() {
        return this.minCost;
    }

    public void setMaxCost(JsonObject rawBody) {
        final JsonElement maxCost = rawBody.get("custom_fields").getAsJsonObject().get("max_cost");
        setFieldValue(maxCost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setMaxCost(maxCost.getAsDouble());
            }
        });
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    public Double getMaxCost() {
        return this.maxCost;
    }

    public void setCost(JsonObject rawBody) {
        final JsonElement cost = rawBody.get("custom_fields").getAsJsonObject().get("cost");
        setFieldValue(cost, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setCost(cost.getAsString());
            }
        });
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost() {
        return this.cost;
    }

    public void setCreditCard(JsonObject rawBody) {
        final JsonElement creditCard = rawBody.get("custom_fields").getAsJsonObject().get("creditcard");
        setFieldValue(creditCard, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setCreditCard(creditCard.getAsString());
            }
        });
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public void setBreakfast(JsonObject rawBody) {
        final JsonElement breakfast = rawBody.get("custom_fields").getAsJsonObject().get("breakfast");
        setFieldValue(breakfast, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setBreakfast(breakfast.getAsString());
            }
        });
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getBreakfast() {
        return this.breakfast;
    }

    public void setCapacity(JsonObject rawBody) {
        final JsonElement capacity = rawBody.get("custom_fields").getAsJsonObject().get("capacity");
        setFieldValue(capacity, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setCapacity(capacity.getAsString());
            }
        });
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCapacity() {
        return this.capacity;
    }

    public void setMetro(JsonObject rawBody) {
        final JsonElement metro = rawBody.get("custom_fields").getAsJsonObject().get("metro");
        setFieldValue(metro, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setMetro(metro.getAsString());
            }
        });
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getMetro() {
        return this.metro;
    }

    public void setTram(JsonObject rawBody) {
        final JsonElement tram = rawBody.get("custom_fields").getAsJsonObject().get("tram");
        setFieldValue(tram, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setTram(tram.getAsString());
            }
        });
    }

    public void setTram(String tram) {
        this.tram = tram;
    }

    public String getTram() {
        return this.tram;
    }

    public void setBus(JsonObject rawBody) {
        final JsonElement bus = rawBody.get("custom_fields").getAsJsonObject().get("bus");
        setFieldValue(bus, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setBus(bus.getAsString());
            }
        });
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getBus() {
        return this.bus;
    }

    public void setPlane(JsonObject rawBody) {
        final JsonElement plane = rawBody.get("custom_fields").getAsJsonObject().get("plane");
        setFieldValue(plane, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setPlane(plane.getAsString());
            }
        });
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getPlane() {
        return this.plane;
    }

    public void setBoat(JsonObject rawBody) {
        final JsonElement boat = rawBody.get("custom_fields").getAsJsonObject().get("boat");
        setFieldValue(boat, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setBoat(boat.getAsString());
            }
        });
    }

    public void setBoat(String boat) {
        this.boat = boat;
    }

    public String getBoat() {
        return this.boat;
    }

    public void setCar(JsonObject rawBody) {
        final JsonElement car = rawBody.get("custom_fields").getAsJsonObject().get("car");
        setFieldValue(car, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setCar(car.getAsString());
            }
        });
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar() {
        return this.car;
    }

    public void setGondola(JsonObject rawBody) {
        final JsonElement gondola = rawBody.get("custom_fields").getAsJsonObject().get("gondola");
        setFieldValue(gondola, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setGondola(gondola.getAsString());
            }
        });
    }

    public void setGondola(String gondola) {
        this.gondola = gondola;
    }

    public String getGondola() {
        return this.gondola;
    }

    public void setTrain(JsonObject rawBody) {
        final JsonElement train = rawBody.get("custom_fields").getAsJsonObject().get("train");
        setFieldValue(train, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setTrain(train.getAsString());
            }
        });
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getTrain() {
        return this.train;
    }

    public void setBike(JsonObject rawBody) {
        final JsonElement bike = rawBody.get("custom_fields").getAsJsonObject().get("bike");
        setFieldValue(bike, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setBike(bike.getAsString());
            }
        });
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public String getBike() {
        return this.bike;
    }

    public void setWalking(JsonObject rawBody) {
        final JsonElement walking = rawBody.get("custom_fields").getAsJsonObject().get("walking");
        setFieldValue(walking, new SetFieldValue() {
            @Override
            public void doSetFieldValue() {
                GuideItem.this.setWalking(walking.getAsString());
            }
        });
    }

    public void setWalking(String walking) {
        this.walking = walking;
    }

    public String getWalking() {
        return this.walking;
    }

    public void setFieldValue(JsonElement value, SetFieldValue callback) {
        if (value != null && !value.isJsonNull() && !value.getAsString().trim().isEmpty()) {
            callback.doSetFieldValue();
        }
    }

    protected interface SetFieldValue {
        void doSetFieldValue();
    }

}
